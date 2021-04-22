package com.innsmouth.library.domain.facade;

import com.innsmouth.library.domain.repository.api.OrderRepository;
import com.innsmouth.library.data.dataobject.Order;

import java.util.List;

public class OrdersFacade{

    private final OrderRepository repository;

    public OrdersFacade(OrderRepository repository) {
        this.repository = repository;
        try {
            repository.connect();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addOrder(Order order) {
        repository.insertOrder(order);
    }

    public Order selectOrderByID(long ID){return repository.selectOrderById(ID);}

    public List<Order> search(Order order){return repository.search(order);}

    public void deleteOrder(Order order) {
        repository.deleteOrder(order);
    }

    public void close() {
        try {
            repository.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Order> findAll() {
        return repository.findAll();
    }
}
