package com.kt.cafeshop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.cafeshop.service.OrderService;
import com.kt.cafeshop.utils.OrderDTO;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/cafeshop/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/getAll")
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrder();
    }

    @GetMapping("/{id}")
    public OrderDTO getById(@PathVariable Integer id) {
        return orderService.getOrderById(id);
    }

    @PostMapping("/create")
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    @PutMapping("/update/{id}")
    public OrderDTO updateOrder(@PathVariable Integer id, @RequestBody OrderDTO orderDTO) {
        return orderService.updateOrder(id, orderDTO);
    }

}
