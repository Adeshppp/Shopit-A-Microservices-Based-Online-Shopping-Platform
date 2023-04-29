package com.OnlineShopping.orderservice.repository;

import com.OnlineShopping.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {

}
