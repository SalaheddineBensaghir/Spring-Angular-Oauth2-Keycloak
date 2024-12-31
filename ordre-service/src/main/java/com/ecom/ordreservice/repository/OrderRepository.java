package com.ecom.ordreservice.repository;

import com.ecom.ordreservice.entites.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
}
