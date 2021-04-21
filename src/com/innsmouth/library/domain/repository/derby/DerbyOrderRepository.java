package com.innsmouth.library.domain.repository.derby;

import com.innsmouth.library.data.query.BookQuery;
import com.innsmouth.library.domain.repository.api.OrderRepository;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigDecimal;

public class DerbyOrderRepository implements OrderRepository {
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
    public long insertOrder(BookQuery bookQuery) {
        String[] queryParams = generateAddParams(bookQuery);
        String sqlAddQuery = generateAddQuery();

        try {
            return dbAccess.insert(connection, sqlAddQuery, new ScalarHandler<BigDecimal>(), queryParams).longValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1L;
    }
}
