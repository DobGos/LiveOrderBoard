package com.hearuexperts.lob;

import com.hearuexperts.lob.repository.OrderRepository;
import com.hearuexperts.lob.repository.OrderRepositoryImpl;
import com.hearuexperts.lob.service.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dobromir Gospodinov
 * @date 27/02/17
 */


@Configuration
public class ApplicationConfiguration {

  @Bean
  public OrderRepository getOrderRepository(){
    return new OrderRepositoryImpl();
  }

  @Bean
  public OrderService getOrderService(){
    return new OrderService(getOrderRepository());
  }
}
