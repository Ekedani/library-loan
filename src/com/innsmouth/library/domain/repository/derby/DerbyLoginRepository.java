package com.innsmouth.library.domain.repository.derby;

import com.innsmouth.library.data.dataobject.User;
import com.innsmouth.library.data.dataobject.Book;
import com.innsmouth.library.domain.repository.api.LoginRepository;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class DerbyLoginRepository implements LoginRepository {

    private static final String DATABASE_PATH = "//localhost:1527/library";
    private Connection connection;

    private final QueryRunner dbAccess = new QueryRunner();

    @Override
    public boolean validateCredentials(String email, String password) {
        try {
            connection = DriverManager.getConnection("jdbc:derby:" + DATABASE_PATH);

            List<User> usersList = dbAccess.query("SELECT * FROM READER WHERE EMAIL=?, PASSWORD=?", new BeanListHandler<>(User.class), email, password);
            if(usersList.isEmpty()) return false;
            else return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void setup() throws Exception {
        //todo
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
