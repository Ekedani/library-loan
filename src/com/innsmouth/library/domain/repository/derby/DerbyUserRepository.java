package com.innsmouth.library.domain.repository.derby;

import com.innsmouth.library.data.dataobject.Book;
import com.innsmouth.library.data.dataobject.User;
import com.innsmouth.library.data.query.UserQuery;
import com.innsmouth.library.domain.repository.api.UserRepository;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class DerbyUserRepository implements UserRepository {
    private static final String DATABASE_PATH = "//localhost:1527/library";
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
        //todo implement
        return false;
    }

    @Override
    public boolean deleteUser(UserQuery user) {
        //todo implement
        return false;
    }

    @Override
    public List<User> findUserByProperty(UserQuery query) {
        //todo implement
        return null;
    }

    @Override
    public List<User> findAll() {
        //todo implement
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
}
