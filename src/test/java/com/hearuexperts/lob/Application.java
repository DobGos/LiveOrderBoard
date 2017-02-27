package com.hearuexperts.lob;

import com.hearuexperts.lob.domain.Order;
import com.hearuexperts.lob.service.OrderService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.hearuexperts.lob.domain.OrderType.BUY;
import static com.hearuexperts.lob.domain.OrderType.SELL;

/**
 * @author Dobromir Gospodinov
 * @date 27/02/17
 */
class Application {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
    ctx.register(ApplicationConfiguration.class);
    ctx.refresh();

    OrderService orderService = ctx.getBean(OrderService.class);
    List<Order> orders = Arrays.asList(
            new Order.OrderBuilder().withUserId("user1").withOrderType(SELL).withPricePerKg(306).withQuantityInKg(3.5).createOrder(),
            new Order.OrderBuilder().withUserId("user2").withOrderType(SELL).withPricePerKg(310).withQuantityInKg(1.2).createOrder(),
            new Order.OrderBuilder().withUserId("user3").withOrderType(SELL).withPricePerKg(307).withQuantityInKg(1.5).createOrder(),
            new Order.OrderBuilder().withUserId("user4").withOrderType(SELL).withPricePerKg(306).withQuantityInKg(2.0).createOrder(),
            new Order.OrderBuilder().withUserId("user1").withOrderType(BUY).withPricePerKg(306).withQuantityInKg(3.5).createOrder(),
            new Order.OrderBuilder().withUserId("user2").withOrderType(BUY).withPricePerKg(310).withQuantityInKg(1.2).createOrder(),
            new Order.OrderBuilder().withUserId("user3").withOrderType(BUY).withPricePerKg(307).withQuantityInKg(1.5).createOrder(),
            new Order.OrderBuilder().withUserId("user4").withOrderType(BUY).withPricePerKg(306).withQuantityInKg(2.0).createOrder());
    List<String> orderIds = new ArrayList<>();
    orders.forEach(order -> orderIds.add(order.getOrderId()));

    orders.forEach(orderService::register);


    System.out.println("SELLS: \n" + orderService.getSellOrdersSummary());
    System.out.println("BUYS: \n" + orderService.getBuyOrdersSummary());


    orderService.cancel(orderIds.get(0));
    orderService.cancel(orderIds.get(7));

    System.out.println("SELLS: \n" + orderService.getSellOrdersSummary());
    System.out.println("BUYS: \n" + orderService.getBuyOrdersSummary());

  }
}
