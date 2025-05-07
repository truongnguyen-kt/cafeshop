package com.kt.cafeshop.service;

import java.util.List;

import com.kt.cafeshop.entities.Order;
import com.kt.cafeshop.utils.OrderDTO;

public interface OrderService {
    public List<OrderDTO> getAllOrder();

    public OrderDTO getOrderById(Integer orderId);

    public OrderDTO createOrder(OrderDTO orderDTO);

    public OrderDTO updateOrder(Integer orderId, OrderDTO orderDTO);

    public List<OrderDTO> getOrderByTableIdWithStatus(Integer tableId, String status);

    public Order mapToOrderObject(OrderDTO dto);
}
