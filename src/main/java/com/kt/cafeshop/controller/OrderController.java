package com.kt.cafeshop.controller;

import com.kt.cafeshop.service.OrderService;
import com.kt.cafeshop.utils.OrderDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cafeshop/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/getAll")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        try {
            List<OrderDTO> orders = orderService.getAllOrder();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            OrderDTO order = orderService.getOrderById(id);
            if (order == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found with ID: " + id);
            }
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to get order: " + e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO) {
        try {
            OrderDTO created = orderService.createOrder(orderDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to create order: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Integer id, @RequestBody OrderDTO orderDTO) {
        try {
            OrderDTO updated = orderService.updateOrder(id, orderDTO);
            if (updated == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found with ID: " + id);
            }
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to update order: " + e.getMessage());
        }
    }
}