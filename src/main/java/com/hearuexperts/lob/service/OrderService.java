package com.hearuexperts.lob.service;

import com.hearuexperts.lob.domain.AggregatedOrder;
import com.hearuexperts.lob.domain.Order;
import com.hearuexperts.lob.domain.OrderType;
import com.hearuexperts.lob.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.hearuexperts.lob.domain.OrderType.BUY;
import static com.hearuexperts.lob.domain.OrderType.SELL;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

/**
 * @author Dobromir Gospodinov
 * @date 27/02/17
 */
@Service
public class OrderService {

  // Because summary is produced separate for BUYS and SELLS, it is reasonable to store them in separate repos.
  // In this exercise we keep it one for simplicity.
  OrderRepository orderRepository;

  @Autowired
  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public void register(Order order) {
    orderRepository.add(order);
  }

  public Order cancel(String orderId) {
    return orderRepository.remove(orderId);
  }

  public List<AggregatedOrder> getSellOrdersSummary() {
    return getOrdersPriceVsQuantityMapFor(SELL).entrySet().stream()
            .map(x -> AggregatedOrder.create(x.getKey(), x.getValue()))
            .sorted(comparing(AggregatedOrder::getPricePerKg))
            .collect(toList());
  }

  public List<AggregatedOrder> getBuyOrdersSummary() {
    return getOrdersPriceVsQuantityMapFor(BUY).entrySet().stream()
            .map(x -> AggregatedOrder.create(x.getKey(), x.getValue()))
            .sorted(comparing(AggregatedOrder::getPricePerKg).reversed())
            .collect(toList());
  }

  private Map<Double, Double> getOrdersPriceVsQuantityMapFor(OrderType orderType) {
    return orderRepository
            .getOrdersFor(orderType)
            .collect(groupingBy(Order::getPricePerKg, summingDouble(Order::getQuantityInKg)));
  }
}
