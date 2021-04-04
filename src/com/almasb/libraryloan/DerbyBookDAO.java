package com.almasb.libraryloan;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import com.almasb.libraryloan.booklist.Book;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

public class DerbyBookDAO implements BookDAO {
    final String DATABASE_PATH = "//localhost:1527/library";

    private Connection connection;

    private QueryRunner dbAccess = new QueryRunner();

    private static final List<Book> EMPTY = new ArrayList<>();

    @Override
    public void setup() throws Exception {

        connection = DriverManager.getConnection("jdbc:derby:" + DATABASE_PATH);

        dbAccess.update(connection, "CREATE TABLE Books ("
                + "uniqueID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                + "name VARCHAR(30), authors VARCHAR(100), publishedYear INTEGER, available BOOLEAN"
                + ")");
    }

    @Override
    public void connect() throws Exception {
        connection = DriverManager.getConnection("jdbc:derby:" + DATABASE_PATH/* + ";create=true"*/);
    }

    @Override
    public void close() throws Exception {
        connection.close();
        try {
            DriverManager.getConnection("jdbc:derby:" + DATABASE_PATH +";shutdown=true");
        }
        catch (Exception e) {}
    }

    @Override
    public long insertBook(Book book) {

        try {
            long id = dbAccess.insert(connection, "INSERT INTO BOOK (title, author, PUBLISHYEAR, COPIESPRESENT) VALUES (?, ?, ?, ?)",
                    new ScalarHandler<BigDecimal>(), book.getTitle(), book.getAuthor(), book.getPublishYear(), 13).longValue();
            return id;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return -1L;
    }

    @Override
    public boolean updateBook(Book book) {

        try {
            dbAccess.update(connection, "UPDATE Book SET title=?, author=?, PUBLISHYEAR=?, COPIESPRESENT=? WHERE BOOKID=?",
                    book.getTitle(), book.getAuthor(), book.getPublishYear(), 555, book.getBookID());
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteBook(Book book) {
        try {
            dbAccess.update(connection, "DELETE FROM book WHERE BOOKID=?", book.getBookID());
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Book> findBookByProperty(BookSearchType searchType, Object value) {
        String whereClause = "";
        String valueClause = "";

        switch (searchType) {
            case AUTHOR:
                whereClause = "author LIKE ?";
                valueClause = "%" + value.toString() + "%";
                break;
            case AVAILABLE:
                whereClause = "available = ?";
                valueClause = value.toString();
                break;
            case ID:
                whereClause = "BOOKID = ?";
                valueClause = value.toString();
                break;
            case NAME:
                whereClause = "TITLE LIKE ?";
                valueClause = "%" + value.toString() + "%";
                break;
            case PUBLISHED_YEAR:
                whereClause = "PUBLISHYEAR = ?";
                valueClause = value.toString();
                break;
            default:
                System.out.println("Unknown search type");
                break;
        }

        try {
            return dbAccess.query(connection, "SELECT * FROM Book WHERE " + whereClause, new BeanListHandler<Book>(Book.class), valueClause);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return EMPTY;
    }

    @Override
    public List<Book> findAll() {
        try {
            return dbAccess.query(connection, "SELECT * FROM Book", new BeanListHandler<Book>(Book.class));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return EMPTY;
    }

}
