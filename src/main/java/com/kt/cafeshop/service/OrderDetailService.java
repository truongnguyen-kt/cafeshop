package com.kt.cafeshop.service;

import java.util.List;

import com.kt.cafeshop.utils.OrderDetailDTO;

public interface OrderDetailService {
    public OrderDetailDTO getDetailById(Integer detailId);

    public List<OrderDetailDTO> getOrderDetailByOrderId(Integer orderId);

    public OrderDetailDTO createOrderDetail(Integer tableId, OrderDetailDTO detailDTO);

    public OrderDetailDTO updateOrderDetail(Integer detailId, OrderDetailDTO detailDTO);

    public OrderDetailDTO deleteOrderDetail(Integer detailId);
}
