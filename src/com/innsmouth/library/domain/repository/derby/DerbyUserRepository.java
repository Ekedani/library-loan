package com.innsmouth.library.domain.repository.derby;

import com.innsmouth.library.data.dataobject.User;
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
import java.util.concurrent.atomic.AtomicReference;

public class DerbyUserRepository implements UserRepository {
    private static final String NAME_COL = "Name";
    private static final String ADDRESS_COL = "Address";
    private static final String EMAIL_COL = "EMail";
    private static final String NUMBER_COL = "Number";

    private static final String DATABASE_PATH = "//localhost:1527/library";

    private static final List<User> EMPTY = new ArrayList<>();

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
            dbAccess.update(connection, "UPDATE READER SET name=?, address=?, phonenumber=?, email=?, password=? WHERE READERID=?",
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
            return dbAccess.query(connection, "SELECT * FROM Reader", new BeanListHandler<>(User.class));
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

    private String createSqlLikeQuery(ArrayList<String> whereClauses) {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT * FROM user WHERE");

        AtomicReference<Boolean> isFirst = new AtomicReference<>(true);
        whereClauses.forEach(clause -> {
            appendClauses(isFirst.get(), sqlQuery, clause);
            isFirst.set(false);
        });

        return sqlQuery.toString();
    }

    private void appendClauses(Boolean isFirst, StringBuilder sqlQuery, String likeClause) {
        String separator;
        if (isFirst) {
            separator = " ";
        } else {
            separator = " OR ";
        }
        sqlQuery.append(separator);

        sqlQuery.append(likeClause);
    }

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
        String nameQueryText = query.getName();
        addLikeClause(whereClauses, valueClauses, nameQueryText, NAME_COL);
    }

    private void addressLikeClause(ArrayList<String> whereClauses, ArrayList<String> valueClauses, UserQuery query) {
        String addressQueryText = query.getAddress();
        addLikeClause(whereClauses, valueClauses, addressQueryText, ADDRESS_COL);
    }

    private void numberLikeClause(ArrayList<String> whereClauses, ArrayList<String> valueClauses, UserQuery query) {
        String numberQueryText = String.valueOf(query.getNumber());
        addLikeClause(whereClauses, valueClauses, numberQueryText, NUMBER_COL);
    }

    private void emailLikeClause(ArrayList<String> whereClauses, ArrayList<String> valueClauses, UserQuery query) {
        String emailQueryText = query.getEmail();
        addLikeClause(whereClauses, valueClauses, emailQueryText, EMAIL_COL);
    }


}
