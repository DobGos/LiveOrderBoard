package com.hearuexperts.lob.service;

import com.hearuexperts.lob.domain.AggregatedOrder;
import com.hearuexperts.lob.domain.Order;
import com.hearuexperts.lob.domain.OrderType;
import com.hearuexperts.lob.repository.OrderRepository;
import org.junit.Test;

import java.util.List;
import java.util.stream.Stream;

import static com.hearuexperts.lob.domain.OrderType.BUY;
import static com.hearuexperts.lob.domain.OrderType.SELL;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Dobromir Gospodinov
 * @date 27/02/17
 */
public class OrderServiceTest {


  @Test
  public void registerShouldAddOrderToTheRepository() {
    // Given
    OrderRepository orderRepository = mock(OrderRepository.class);
    OrderService orderService = new OrderService(orderRepository);
    Order order = new Order.OrderBuilder().withUserId("user1").withOrderType(SELL).withPricePerKg(100.11).withQuantityInKg(5).createOrder();

    // When
    orderService.register(order);

    // Then
    verify(orderRepository).add(order);
  }

  @Test
  public void cancelShouldRemoveOrderFromRepository() {
    // Given
    OrderRepository orderRepository = mock(OrderRepository.class);
    OrderService orderService = new OrderService(orderRepository);
    String orderId = "1";

    // When
    orderService.cancel(orderId);

    // Then
    verify(orderRepository).remove(orderId);
  }

  @Test
  public void getSellOrdersSummary() {
    // Given
    OrderRepository orderRepository = mock(OrderRepository.class);
    OrderService orderService = new OrderService(orderRepository);
    Stream<Order> orders = Stream.of(
            new Order.OrderBuilder().withUserId("user1").withOrderType(SELL).withPricePerKg(306).withQuantityInKg(3.5).createOrder(),
            new Order.OrderBuilder().withUserId("user2").withOrderType(SELL).withPricePerKg(310).withQuantityInKg(1.2).createOrder(),
            new Order.OrderBuilder().withUserId("user3").withOrderType(SELL).withPricePerKg(307).withQuantityInKg(1.5).createOrder(),
            new Order.OrderBuilder().withUserId("user4").withOrderType(SELL).withPricePerKg(306).withQuantityInKg(2.0).createOrder());
    when(orderRepository.getOrdersFor(any(OrderType.class))).thenReturn(orders);

    // When
    List<AggregatedOrder> sellOrderSummary = orderService.getSellOrdersSummary();

    // Then
    verify(orderRepository).getOrdersFor(SELL);
    assertEquals(3, sellOrderSummary.size());

    assertEquals(306, sellOrderSummary.get(0).getPricePerKg(), 0.001);
    assertEquals(5.5, sellOrderSummary.get(0).getQuantityInKg(), 0.001);

    assertEquals(307, sellOrderSummary.get(1).getPricePerKg(), 0.001);
    assertEquals(1.5, sellOrderSummary.get(1).getQuantityInKg(), 0.001);

    assertEquals(310, sellOrderSummary.get(2).getPricePerKg(), 0.001);
    assertEquals(1.2, sellOrderSummary.get(2).getQuantityInKg(), 0.001);
  }

  @Test
  public void getBuyOrdersSummary() {
    // Given
    OrderRepository orderRepository = mock(OrderRepository.class);
    OrderService orderService = new OrderService(orderRepository);
    Stream<Order> orders = Stream.of(
            new Order.OrderBuilder().withUserId("user1").withOrderType(BUY).withPricePerKg(306).withQuantityInKg(3.5).createOrder(),
            new Order.OrderBuilder().withUserId("user2").withOrderType(BUY).withPricePerKg(310).withQuantityInKg(1.2).createOrder(),
            new Order.OrderBuilder().withUserId("user3").withOrderType(BUY).withPricePerKg(307).withQuantityInKg(1.5).createOrder(),
            new Order.OrderBuilder().withUserId("user4").withOrderType(BUY).withPricePerKg(306).withQuantityInKg(2.0).createOrder());
    when(orderRepository.getOrdersFor(any(OrderType.class))).thenReturn(orders);

    // When
    List<AggregatedOrder> sellOrderSummary = orderService.getBuyOrdersSummary();

    // Then
    verify(orderRepository).getOrdersFor(BUY);
    assertEquals(3, sellOrderSummary.size());
    assertEquals(310, sellOrderSummary.get(0).getPricePerKg(), 0.001);
    assertEquals(1.2, sellOrderSummary.get(0).getQuantityInKg(), 0.001);

    assertEquals(307, sellOrderSummary.get(1).getPricePerKg(), 0.001);
    assertEquals(1.5, sellOrderSummary.get(1).getQuantityInKg(), 0.001);

    assertEquals(306, sellOrderSummary.get(2).getPricePerKg(), 0.001);
    assertEquals(5.5, sellOrderSummary.get(2).getQuantityInKg(), 0.001);

  }

}