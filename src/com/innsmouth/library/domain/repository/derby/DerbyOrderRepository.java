package com.innsmouth.library.domain.repository.derby;

import com.innsmouth.library.domain.repository.api.OrderRepository;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import com.innsmouth.library.data.dataobject.Order;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

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

    }

    @Override
    public void connect() throws Exception {

    }

    @Override
    public void close() throws Exception {

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

    private String generateAddQuery() {
        return "INSERT INTO ORDERS (ID, book, take_date, return_date) VALUES (?, ?, ?, ?)";
    }

    private String[] generateAddParams(Order order) {
        final int paramsNum = 4;
        String[] result = new String[paramsNum];
        result[0] = String.valueOf(order.getReaderId());
        result[1] = order.getBookId();
        result[2] = order.getTake_date();
        result[3] = order.getReturn_date();

        return result;
    }

    @Override
    public boolean deleteOrder(Order order) {
        try {
            dbAccess.update(connection, "DELETE FROM ORDERS WHERE ORDERID=?", order.getOrderId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Order selectOrderById(long ID){
        try {
            List<Order> orderList = dbAccess.query(connection, "select * from orders where ORDERID=?", new BeanListHandler<>(Order.class), ID);
            return orderList.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Order();
    }

    @Override
    public List<Order> findAll() {
        try {
            return dbAccess.query(connection, "SELECT * FROM Orders", new BeanListHandler<Order>(Order.class));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return EMPTY;
    }
}
