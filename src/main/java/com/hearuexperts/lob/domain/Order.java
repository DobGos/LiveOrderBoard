package com.hearuexperts.lob.domain;

import java.util.UUID;

/**
 * @author Dobromir Gospodinov
 * @date 27/02/17
 */
public class Order {

  private String orderId;
  private String userId;
  private double quantityInKg;
  private double pricePerKg;
  private OrderType orderType;


  Order(String orderId, String userId, double quantityInKg, double pricePerKg, OrderType orderType) {
    this.orderId = orderId;
    this.userId = userId;
    this.quantityInKg = quantityInKg;
    this.pricePerKg = pricePerKg;
    this.orderType = orderType;
  }

  public String getOrderId() {
    return orderId;
  }

  public String getUserId() {
    return userId;
  }

  public double getQuantityInKg() {
    return quantityInKg;
  }

  public double getPricePerKg() {
    return pricePerKg;
  }

  public OrderType getOrderType() {
    return orderType;
  }

  public void setOrderType(OrderType orderType) {
    this.orderType = orderType;
  }

  public static class OrderBuilder {
    private String orderId = UUID.randomUUID().toString();
    private String userId;
    private double quantityInKg;
    private double pricePerKg;
    private OrderType orderType;

    public OrderBuilder withUserId(String userId) {
      this.userId = userId;
      return this;
    }

    public OrderBuilder withQuantityInKg(double quantityInKg) {
      this.quantityInKg = quantityInKg;
      return this;
    }

    public OrderBuilder withPricePerKg(double pricePerKg) {
      this.pricePerKg = pricePerKg;
      return this;
    }

    public OrderBuilder withOrderType(OrderType orderType) {
      this.orderType = orderType;
      return this;
    }

    public Order createOrder() {
      return new Order(orderId, userId, quantityInKg, pricePerKg, orderType);
    }
  }
}
