package com.hearuexperts.lob.domain;

/**
 * @author Dobromir Gospodinov
 * @date 27/02/17
 */
public class AggregatedOrder {
  private double quantityInKg;
  private double pricePerKg;

  public static AggregatedOrder create(Double pricePerKg, Double quantityInKg){
    AggregatedOrder aggregatedOrder = new AggregatedOrder();
    aggregatedOrder.setPricePerKg(pricePerKg);
    aggregatedOrder.setQuantityInKg(quantityInKg);
    return aggregatedOrder;
  }
  public double getQuantityInKg() {
    return quantityInKg;
  }

  public void setQuantityInKg(double quantityInKg) {
    this.quantityInKg = quantityInKg;
  }

  public double getPricePerKg() {
    return pricePerKg;
  }

  public void setPricePerKg(double pricePerKg) {
    this.pricePerKg = pricePerKg;
  }

  @Override
  public String toString() {
    return "AggregatedOrder{" +
            "quantityInKg=" + quantityInKg +
            ", pricePerKg=" + pricePerKg +
            '}';
  }
}
