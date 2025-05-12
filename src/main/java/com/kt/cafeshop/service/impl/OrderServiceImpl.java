package com.kt.cafeshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kt.cafeshop.entities.Order;
import com.kt.cafeshop.repository.OrderRepository;
import com.kt.cafeshop.service.OrderService;
import com.kt.cafeshop.utils.OrderDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public List<OrderDTO> getAllOrder() {
        return orderRepository.findAll().stream().map(this::mapToOrderDTO).toList();
    }

    @Override
    public OrderDTO getOrderById(Integer orderId) {
        Optional<Order> result = orderRepository.findById(orderId);
        return result.map(this::mapToOrderDTO).orElse(null);
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setTableId(orderDTO.getTableId());
        order.setTotalPrice(orderDTO.getTotalPrice() != null ? orderDTO.getTotalPrice() : 0);
        order.setStatus(orderDTO.getStatus() != null ? orderDTO.getStatus() : "PENDING");
        order.setMethod(null);
        return mapToOrderDTO(orderRepository.save(order));
    }

    @Override
    public OrderDTO updateOrder(Integer orderId, OrderDTO orderDTO) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            Order savedOrder = order.get();

            savedOrder.setTableId(orderDTO.getTableId());
            savedOrder.setTotalPrice(orderDTO.getTotalPrice());
            savedOrder.setStatus(orderDTO.getStatus());
            savedOrder.setMethod(orderDTO.getMethod());

            savedOrder = orderRepository.save(savedOrder);
            return mapToOrderDTO(savedOrder);
        } else {
            return null;
        }
    }

    private OrderDTO mapToOrderDTO(Order order) {
        return new OrderDTO(order.getOrderId(), order.getTableId(), order.getTotalPrice(), order.getStatus(),
                order.getMethod(), order.getCreatedDate());
    }

    @Override
    public List<OrderDTO> getOrderByTableIdWithStatus(Integer tableId, String status) {
        return orderRepository.findAllByTableIdAndStatus(tableId, status).stream().map(this::mapToOrderDTO).toList();
    }

    @Override
    public Order mapToOrderObject(OrderDTO dto) {
        return new Order(dto.getOrderId(), dto.getTableId(), dto.getTotalPrice(), dto.getStatus(), dto.getMethod(),
                dto.getCreatedDate());
    }

}
