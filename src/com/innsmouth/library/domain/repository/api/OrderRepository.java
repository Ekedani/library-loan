package com.innsmouth.library.domain.repository.api;

import com.innsmouth.library.data.dataobject.Order;
import com.innsmouth.library.domain.repository.base.Repository;

import java.util.List;

public interface OrderRepository extends Repository {
   List<Order> findAll();

   Order selectOrderById(long ID);

   boolean deleteOrder(Order order);

   long insertOrder(Order order);

    List<Order> search(Order order);
}
