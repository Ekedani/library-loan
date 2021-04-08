package com.innsmouth.library.domain.repository.derby;

import com.innsmouth.library.data.dataobject.Book;
import com.innsmouth.library.data.dataobject.User;
import com.innsmouth.library.data.query.BookQuery;
import com.innsmouth.library.data.query.UserQuery;
import com.innsmouth.library.domain.repository.api.UserRepository;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class DerbyUserRepository implements UserRepository {
    private static final String DATABASE_PATH = "//localhost:1527/library";
    //TODO: Добавить columns
    private static final List<Book> EMPTY = new ArrayList<>();

    private Connection connection;
    private final QueryRunner dbAccess = new QueryRunner();

    @Override
    public void insertUser(UserQuery user) {
       try {
           dbAccess.insert(connection, "INSERT INTO READER (name, address, phonenumber, email, password) VALUES (?, ?, ?, ?, ?)",
                   new ScalarHandler<BigDecimal>(), user.getName(), user.getAddress(), user.getNumber(), user.getEmail(), user.getPassword());
       } catch (Exception e) {
           e.printStackTrace();
       }

    }

    @Override
    public boolean updateUser(UserQuery user) {
        try {
            dbAccess.update(connection, "UPDATE User SET name=?, address=?, phonenumber=?, email=?, password=? WHERE READERID=?",
                    user.getName(), user.getAddress(), user.getNumber(), user.getEmail(), user.getPassword(), user.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteUser(UserQuery user) {
        try {
            dbAccess.update(connection, "DELETE FROM reader WHERE READERID=?", user.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> findUserByProperty(UserQuery query) {
        ArrayList<String> whereClauses = new ArrayList<>();
        ArrayList<String> valueClauses = new ArrayList<>();

        generateClauses(whereClauses, valueClauses, query);

        String sqlQuery = createSqlLikeQuery(whereClauses);
        System.out.println(sqlQuery);

        try {
            return dbAccess.query(connection, sqlQuery, new BeanListHandler<>(User.class), valueClauses.stream().toArray(String[]::new));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<User> findAll() {
        try {
            return dbAccess.query(connection, "SELECT * FROM Reader", new BeanListHandler<User>(User.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setup() throws Exception {
        connection = DriverManager.getConnection("jdbc:derby:" + DATABASE_PATH);

        dbAccess.update(connection, "CREATE TABLE reader (\n" +
                "   readerID INT NOT NULL GENERATED ALWAYS AS IDENTITY,\n" +
                "   name varchar (50) NOT NULL,\n" +
                "   address varchar (100) NOT NULL,\n" +
                "   phoneNumber INT NOT NULL,\n" +
                "   email varchar (150) NOT NULL,\n" +
                "   password varchar (50) NOT NULL,\n" +
                "   PRIMARY KEY (readerID)\n" +
                ")");
    }

    @Override
    public void connect() throws Exception {
        connection = DriverManager.getConnection("jdbc:derby:" + DATABASE_PATH/* + ";create=true"*/);
    }

    @Override
    public void close() throws Exception {
        connection.close();
        try {
            DriverManager.getConnection("jdbc:derby:" + DATABASE_PATH + ";shutdown=true");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO: Прописать клаузы
    private void generateClauses(ArrayList<String> whereClauses, ArrayList<String> valueClauses, UserQuery query) {
        nameLikeClause(whereClauses, valueClauses, query);
        addressLikeClause(whereClauses, valueClauses, query);
        emailLikeClause(whereClauses, valueClauses, query);
        numberLikeClause(whereClauses, valueClauses, query);
    }

    private void addLikeClause(ArrayList<String> whereClauses, ArrayList<String> valueClause,
                               String textToFind, String column) {
        if (textToFind == null || textToFind.isEmpty()) return;

        String whereText = column + " LIKE ?";
        String valueText = "%" + textToFind + "%";

        whereClauses.add(whereText);
        valueClause.add(valueText);
    }

    private void nameLikeClause(ArrayList<String> whereClauses, ArrayList<String> valueClauses, UserQuery query) {
        String genreQueryText = query.getName();
        addLikeClause(whereClauses, valueClauses, genreQueryText, NAME_COL);
    }

    private void addressLikeClause(ArrayList<String> whereClauses, ArrayList<String> valueClauses, UserQuery query) {
        String genreQueryText = query.getName();
        addLikeClause(whereClauses, valueClauses, genreQueryText, ADDRESS_COL);
    }

    private void numberLikeClause(ArrayList<String> whereClauses, ArrayList<String> valueClauses, UserQuery query) {
        String genreQueryText = query.getName();
        addLikeClause(whereClauses, valueClauses, genreQueryText, NUMBER_COL);
    }

    private void emailLikeClause(ArrayList<String> whereClauses, ArrayList<String> valueClauses, UserQuery query) {
        String genreQueryText = query.getName();
        addLikeClause(whereClauses, valueClauses, genreQueryText, EMAIL_COL);
    }


}
