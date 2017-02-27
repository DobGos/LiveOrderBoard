package com.hearuexperts.lob.repository;

import com.hearuexperts.lob.domain.Order;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.hearuexperts.lob.domain.OrderType.BUY;
import static com.hearuexperts.lob.domain.OrderType.SELL;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Dobromir Gospodinov
 * @date 27/02/17
 */
public class OrderRepositoryImplTest {

  @Test
  public void addedOrderShouldBeAvailable() {
    // Given
    List<Order> orders = Arrays.asList(
            new Order.OrderBuilder().withUserId("user1").withOrderType(SELL).withPricePerKg(306).withQuantityInKg(3.5).createOrder(),
            new Order.OrderBuilder().withUserId("user2").withOrderType(SELL).withPricePerKg(310).withQuantityInKg(1.2).createOrder(),
            new Order.OrderBuilder().withUserId("user3").withOrderType(BUY).withPricePerKg(307).withQuantityInKg(1.5).createOrder(),
            new Order.OrderBuilder().withUserId("user4").withOrderType(BUY).withPricePerKg(306).withQuantityInKg(2.0).createOrder());

    OrderRepositoryImpl orderRepository = new OrderRepositoryImpl();

    // When
    orders.forEach(orderRepository::add);
    List<Order> sells = orderRepository.getOrdersFor(SELL).collect(toList());
    List<Order> buys = orderRepository.getOrdersFor(BUY).collect(toList());

    // Then
    assertTrue(sells.size() == 2);
    assertTrue(sells.contains(orders.get(0)));
    assertTrue(sells.contains(orders.get(1)));

    assertTrue(buys.size() == 2);
    assertTrue(buys.contains(orders.get(2)));
    assertTrue(buys.contains(orders.get(3)));
  }

  @Test
  public void removedOrderShouldNotBeAvailable() {
    // Given
    List<Order> orders = Arrays.asList(
            new Order.OrderBuilder().withUserId("user1").withOrderType(SELL).withPricePerKg(306).withQuantityInKg(3.5).createOrder(),
            new Order.OrderBuilder().withUserId("user2").withOrderType(SELL).withPricePerKg(310).withQuantityInKg(1.2).createOrder(),
            new Order.OrderBuilder().withUserId("user3").withOrderType(BUY).withPricePerKg(307).withQuantityInKg(1.5).createOrder(),
            new Order.OrderBuilder().withUserId("user4").withOrderType(BUY).withPricePerKg(306).withQuantityInKg(2.0).createOrder());

    List<String> orderIds = orders.stream().map(order -> order.getOrderId()).collect(toList());
    OrderRepositoryImpl orderRepository = new OrderRepositoryImpl();
    orders.forEach(orderRepository::add);

    // When
    orderRepository.remove(orderIds.get(0));
    orderRepository.remove(orderIds.get(3));
    List<Order> sells = orderRepository.getOrdersFor(SELL).collect(toList());
    List<Order> buys = orderRepository.getOrdersFor(BUY).collect(toList());

    // Then
    assertTrue(sells.size() == 1);
    assertFalse(sells.contains(orders.get(0)));
    assertTrue(sells.contains(orders.get(1)));

    assertTrue(buys.size() == 1);
    assertTrue(buys.contains(orders.get(2)));
    assertFalse(buys.contains(orders.get(3)));
  }
}