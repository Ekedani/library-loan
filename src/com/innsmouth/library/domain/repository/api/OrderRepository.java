package com.innsmouth.library.domain.repository.api;

import com.innsmouth.library.data.dataobject.Order;
import com.innsmouth.library.domain.repository.base.Repository;

import java.util.List;

public interface OrderRepository extends Repository {
    public List<Order> findAll();

    public Order selectOrderById(long ID);

    public boolean deleteOrder(Order order);

    public long insertOrder(Order order);

    //TODO: Пидоры вы суки
}
