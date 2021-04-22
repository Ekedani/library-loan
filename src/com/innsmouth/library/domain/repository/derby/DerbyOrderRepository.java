package com.innsmouth.library.domain.repository.derby;

import com.innsmouth.library.data.dataobject.User;
import com.innsmouth.library.domain.repository.api.OrderRepository;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import com.innsmouth.library.data.dataobject.Order;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class DerbyOrderRepository implements OrderRepository {
    private static final String ID_COL = "Reader ID";
    private static final String BOOK_COL = "Book";
    private static final String TAKE_DATE_COL = "Take Date";
    private static final String RETURN_DATE_COL = "Return Date";

    private static final String DATABASE_PATH = "//localhost:1527/library";
    private static final List<Order> EMPTY = new ArrayList<>();

    private Connection connection;

    private final QueryRunner dbAccess = new QueryRunner();

    @Override
    public void setup() throws Exception {
        connection = DriverManager.getConnection("jdbc:derby:" + DATABASE_PATH);

        dbAccess.update(connection, "CREATE TABLE Orders ("
                + "uniqueID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                + "ordererid BIGINT, bookid BIGINT, tookDate VARCHAR(30), returndate VARCHAR(30), returned BOOLEAN"
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
    public long insertOrder(Order order) {
        String[] queryParams = generateAddParams(order);
        String sqlAddQuery = generateAddQuery();

        try {
            return dbAccess.insert(connection, sqlAddQuery, new ScalarHandler<BigDecimal>(), queryParams).longValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1L;
    }

    @Override
    public List<Order> search(Order query) {
        ArrayList<String> whereClauses = new ArrayList<>();
        ArrayList<String> valueClauses = new ArrayList<>();

        generateClauses(whereClauses, valueClauses, query);

        String sqlQuery = createSqlLikeQuery(whereClauses);
        System.out.println(sqlQuery);

        try {
            return dbAccess.query(connection, sqlQuery, new BeanListHandler<>(Order.class), valueClauses.stream().toArray(String[]::new));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void update(Order order) {
        try {
            dbAccess.update(connection, "UPDATE ORDERS SET returned =? WHERE UNIQUEID=?",
                    order.getReturned(), order.getUniqueId());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void generateClauses(ArrayList<String> whereClauses, ArrayList<String> valueClauses, Order query) {
        addEqualsClause(whereClauses, valueClauses, query.getBookId(), "bookid");
        addEqualsClause(whereClauses, valueClauses, String.valueOf(query.getOrdererId()), "ordererid");
        addEqualsClause(whereClauses, valueClauses, query.getReturned(), "returned");
    }

    private String createSqlLikeQuery(ArrayList<String> whereClauses) {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT * FROM ORDERS WHERE");

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
            separator = " AND ";
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

    private String generateAddQuery() {
        return "INSERT INTO ORDERS (ID, book, take_date, return_date) VALUES (?, ?, ?, ?)";
    }

    private String[] generateAddParams(Order order) {
        final int paramsNum = 4;
        String[] result = new String[paramsNum];
        result[0] = String.valueOf(order.getOrdererId());
        result[1] = order.getBookId();
        result[2] = order.getTookDate();
        result[3] = order.getReturnDate();

        return result;
    }

    @Override
    public boolean deleteOrder(Order order) {
        try {
            dbAccess.update(connection, "DELETE FROM ORDERS WHERE UNIQUEID =?", order.getUniqueId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Order selectOrderById(long ID) {
        try {
            List<Order> orderList = dbAccess.query(connection, "select * from orders where UNIQUEID=?", new BeanListHandler<>(Order.class), ID);
            return orderList.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Order();
    }

    @Override
    public List<Order> findAll() {
        try {
            return dbAccess.query(connection, "SELECT * FROM Orders", new BeanListHandler<>(Order.class));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return EMPTY;
    }
}
