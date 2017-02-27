package com.hearuexperts.lob.repository;

import com.hearuexperts.lob.domain.Order;
import com.hearuexperts.lob.domain.OrderType;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * An in-memory implementation.
 * @author Dobromir Gospodinov
 * @date 27/02/17
 */
@Repository
public class OrderRepositoryImpl implements OrderRepository {

  ConcurrentHashMap<String, Order> repository = new ConcurrentHashMap<>();

  @Override
  public void add(Order order) {
    repository.put(order.getOrderId(), order);
  }

  @Override
  public Order remove(String orderId) {
    return repository.remove(orderId);
  }

  @Override
  public Stream<Order> getOrdersFor(OrderType orderType) {
    return repository.values().stream().filter(order -> order.getOrderType() == orderType);
  }
}
