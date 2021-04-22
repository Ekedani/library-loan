package com.innsmouth.library.domain.repository.derby;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import com.innsmouth.library.data.dataobject.Book;
import com.innsmouth.library.domain.repository.api.BookRepository;
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
    public long insertBook(Book Book) {
        String[] queryParams = generateAddParams(Book);
        String sqlAddQuery = generateAddQuery();

        try {
            return dbAccess.insert(connection, sqlAddQuery, new ScalarHandler<BigDecimal>(), queryParams).longValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1L;
    }

    private String generateAddQuery() {
        return "INSERT INTO BOOK (title, author, genre, PUBLISHYEAR, annotation, COPIESGIVEN, copiespresent) VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    private String[] generateAddParams(Book Book) {
        final int paramsNum = 7;
        String[] result = new String[paramsNum];
        result[0] = Book.getTitle();
        result[1] = Book.getAuthor();
        result[2] = Book.getGenre();
        result[3] = Integer.toString(Book.getPublishYear());
        result[4] = Book.getAnnotation();
        result[5] = Integer.toString( Book.getCopiesGiven());
        result[6] = Integer.toString( Book.getCopiesPresent());

        return result;
    }

    @Override
    public boolean updateBook(Book book) {

        try {
            dbAccess.update(connection, "UPDATE Book SET title=?, author=?, PUBLISHYEAR=?, COPIESPRESENT=?, GENRE=?, ANNOTATION=? WHERE BOOKID=?",
                    book.getTitle(), book.getAuthor(), book.getPublishYear(), book.getCopiesPresent(), book.getGenre(), book.getAnnotation(), book.getBookID());
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
    public List<Book> findBookByProperty(Book query) {
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

    @Override
    public Book selectBookById(long ID){
        try {
            List<Book> bookList = dbAccess.query(connection, "select * from book where BOOKID=?", new BeanListHandler<>(Book.class), ID);
            return bookList.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Book();
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

    private void generateClauses(ArrayList<String> whereClauses, ArrayList<String> valueClauses, Book query) {
        authorLikeClause(whereClauses, valueClauses, query);
        genreLikeClause(whereClauses, valueClauses, query);
        yearLikeClause(whereClauses, valueClauses, query);
        titleLikeClause(whereClauses, valueClauses, query);
    }

    private void genreLikeClause(ArrayList<String> whereClauses, ArrayList<String> valueClauses, Book query) {
        String genreQueryText = query.getGenre();
        addLikeClause(whereClauses, valueClauses, genreQueryText, GENRE_COL);
    }

    private void authorLikeClause(ArrayList<String> whereClauses, ArrayList<String> valueClauses, Book query) {
        String authorQueryText = query.getAuthor();
        addLikeClause(whereClauses, valueClauses, authorQueryText, AUTHOR_COL);
    }

    private void yearLikeClause(ArrayList<String> whereClauses, ArrayList<String> valueClauses, Book query) {
        String yearQueryText = String.valueOf(query.getPublishYear());
        addEqualsClause(whereClauses, valueClauses, yearQueryText, YEAR_COL);
    }

    private void titleLikeClause(ArrayList<String> whereClauses, ArrayList<String> valueClauses, Book query) {
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
