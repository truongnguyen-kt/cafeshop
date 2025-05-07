package com.kt.cafeshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kt.cafeshop.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByTableIdAndStatus(Integer tableId, String status);
}
