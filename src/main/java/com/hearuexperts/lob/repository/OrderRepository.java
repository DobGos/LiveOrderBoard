package com.hearuexperts.lob.repository;

import com.hearuexperts.lob.domain.Order;
import com.hearuexperts.lob.domain.OrderType;

import java.util.stream.Stream;

/**
 * @author Dobromir Gospodinov
 * @date 27/02/17
 */
public interface OrderRepository {

  void add(Order order);

  Order remove(String orderId);

  Stream<Order> getOrdersFor(OrderType orderType);
}
