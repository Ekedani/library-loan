package com.almasb.libraryloan;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import com.almasb.libraryloan.booklist.Book;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

public class DerbyBookRepository implements BookRepository {
    private static final String AUTHOR_COL = "author";
    private static final String TITLE_COL = "title";
    private static final String GENRE_COL = "genre";
    private static final String YEAR_COL = "publishYear";

    private static final String DATABASE_PATH = "//localhost:1527/library";
    private static final List<Book> EMPTY = new ArrayList<>();

    private Connection connection;

    private final QueryRunner dbAccess = new QueryRunner();

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
            DriverManager.getConnection("jdbc:derby:" + DATABASE_PATH + ";shutdown=true");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public long insertBook(Book book) {

        try {
            return dbAccess.insert(connection, "INSERT INTO BOOK (title, author, PUBLISHYEAR, COPIESPRESENT) VALUES (?, ?, ?, ?)",
                    new ScalarHandler<BigDecimal>(), book.getTitle(), book.getAuthor(), book.getPublishYear(), 13).longValue();
        } catch (Exception e) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteBook(Book book) {
        try {
            dbAccess.update(connection, "DELETE FROM book WHERE BOOKID=?", book.getBookID());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Book> findBookByProperty(BookQuery query) {
        ArrayList<String> whereClauses = new ArrayList<>();
        ArrayList<String> valueClauses = new ArrayList<>();

        generateClauses(whereClauses, valueClauses, query);

        String sqlQuery = createSqlLikeQuery(whereClauses);
        System.out.println(sqlQuery);

        try {
            return dbAccess.query(connection, sqlQuery, new BeanListHandler<>(Book.class), valueClauses.stream().toArray(String[]::new));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return EMPTY;
    }

    private String createSqlLikeQuery(ArrayList<String> whereClauses) {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT * FROM book WHERE");

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

    private void addEqualsClause(ArrayList<String> whereClauses, ArrayList<String> valueClause,
                                 String textToFind, String column) {
        if (textToFind == null || textToFind.isEmpty()) return;

        String whereText = column + " = ?";
        String valueText = textToFind;

        whereClauses.add(whereText);
        valueClause.add(valueText);
    }

    private void addLikeClause(ArrayList<String> whereClauses, ArrayList<String> valueClause,
                               String textToFind, String column) {
        if (textToFind == null || textToFind.isEmpty()) return;

        String whereText = column + " LIKE ?";
        String valueText = "%" + textToFind + "%";

        whereClauses.add(whereText);
        valueClause.add(valueText);
    }

    private void generateClauses(ArrayList<String> whereClauses, ArrayList<String> valueClauses, BookQuery query) {
        authorLikeClause(whereClauses, valueClauses, query);
        genreLikeClause(whereClauses, valueClauses, query);
        yearLikeClause(whereClauses, valueClauses, query);
        titleLikeClause(whereClauses, valueClauses, query);
    }

    private void genreLikeClause(ArrayList<String> whereClauses, ArrayList<String> valueClauses, BookQuery query) {
        String genreQueryText = query.getGenre();
        addLikeClause(whereClauses, valueClauses, genreQueryText, GENRE_COL);
    }

    private void authorLikeClause(ArrayList<String> whereClauses, ArrayList<String> valueClauses, BookQuery query) {
        String authorQueryText = query.getAuthor();
        addLikeClause(whereClauses, valueClauses, authorQueryText, AUTHOR_COL);
    }

    private void yearLikeClause(ArrayList<String> whereClauses, ArrayList<String> valueClauses, BookQuery query) {
        String yearQueryText = String.valueOf(query.getPublishYear());
        addEqualsClause(whereClauses, valueClauses, yearQueryText, YEAR_COL);
    }

    private void titleLikeClause(ArrayList<String> whereClauses, ArrayList<String> valueClauses, BookQuery query) {
        String titleQueryText = query.getTitle();
        addLikeClause(whereClauses, valueClauses, titleQueryText, TITLE_COL);
    }

    @Override
    public List<Book> findAll() {
        try {
            return dbAccess.query(connection, "SELECT * FROM Book", new BeanListHandler<Book>(Book.class));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return EMPTY;
    }

}
