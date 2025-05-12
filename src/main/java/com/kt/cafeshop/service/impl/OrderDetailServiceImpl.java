package com.kt.cafeshop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kt.cafeshop.entities.Order;
import com.kt.cafeshop.entities.OrderDetail;
import com.kt.cafeshop.entities.Product;
import com.kt.cafeshop.repository.OrderDetailRepository;
import com.kt.cafeshop.service.OrderDetailService;
import com.kt.cafeshop.service.OrderService;
import com.kt.cafeshop.service.ProductService;
import com.kt.cafeshop.utils.OrderDTO;
import com.kt.cafeshop.utils.OrderDetailDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository detailRepository;
    private final OrderService orderService;
    private final ProductService productService;

    @Override
    public OrderDetailDTO getDetailById(Integer detailId) {
        Optional<OrderDetail> optionalDetail = detailRepository.findById(detailId);
        if (optionalDetail.isPresent()) {
            return mapToDetailDTO(optionalDetail.get());
        } else {
            return null;
        }
    }

    @Override
    public List<OrderDetailDTO> getOrderDetailByOrderId(Integer orderId) {
        OrderDTO order = orderService.getOrderById(orderId);
        if (order == null) {
            return null;
        } else {
            return detailRepository.findByOrder_OrderId(orderId).stream().map(this::mapToDetailDTO).toList();
        }
    }

    @Override
    public List<OrderDetailDTO> createOrderDetail(Integer tableId, List<OrderDetailDTO> listDetail) {
        Order order;
        List<OrderDetailDTO> responseList = new ArrayList<>();
        if (listDetail == null || listDetail.isEmpty()) {
            throw new IllegalAccessError("detail list is null");
        }
        for (OrderDetailDTO detailDTO : listDetail) {
            // if (detailDTO.getOrderId() != null) {
            // // Đang thêm món vào order đã có
            // OrderDTO orderDTO = orderService.getOrderById(detailDTO.getOrderId());

            // if (orderDTO == null) {
            // throw new IllegalArgumentException("Order không tồn tại!");
            // }

            // if (!"PENDING".equalsIgnoreCase(orderDTO.getStatus())) {
            // throw new IllegalStateException("Không thể thêm món vào order đã được xử
            // lý!");
            // }
            // return null;
            // } else {
            List<OrderDTO> orderList = orderService.getOrderByTableIdWithStatus(tableId, "PENDING");
            if (orderList.isEmpty() || orderList == null) {
                OrderDTO newOrder = new OrderDTO();
                newOrder.setTableId(tableId);
                newOrder.setTotalPrice(0.0);
                newOrder.setStatus("PENDING");

                OrderDTO createdOrder = orderService.createOrder(newOrder);
                order = new Order();
                order.setOrderId(createdOrder.getOrderId());
                order.setTableId(tableId);
            } else {
                order = orderService.mapToOrderObject(orderList.get(0));
            }

            Product product = productService.mapToProductEntity(
                    productService.getProductById(detailDTO.getProductId()));

            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(product);
            detail.setOptionNote(detailDTO.getOptionNote());
            detail.setQuantity(detailDTO.getQuantity());
            detail.setTotalPrice(detailDTO.getQuantity() * product.getPrice());

            OrderDetail saved = detailRepository.save(detail);
            responseList.add(mapToDetailDTO(saved));
            // return mapToDetailDTO(saved);
            // }
        }
        return responseList;
    }

    @Override
    public OrderDetailDTO updateOrderDetail(Integer detailId, OrderDetailDTO detailDTO) {
        Optional<OrderDetail> optionalDetail = detailRepository.findById(detailId);
        if (optionalDetail.isEmpty()) {
            throw new IllegalArgumentException("Order detail không tồn tại!");
        }

        OrderDetail existingDetail = optionalDetail.get();

        // Chỉ cho phép cập nhật nếu order đang ở trạng thái PENDING
        OrderDTO orderDTO = orderService.getOrderById(existingDetail.getOrder().getOrderId());
        if (!"PENDING".equalsIgnoreCase(orderDTO.getStatus())) {
            throw new IllegalStateException("Không thể cập nhật món trong order đã xử lý!");
        }

        // Cập nhật các trường
        Product product = productService.mapToProductEntity(
                productService.getProductById(detailDTO.getProductId()));
        existingDetail.setQuantity(detailDTO.getQuantity());
        existingDetail.setOptionNote(detailDTO.getOptionNote());
        existingDetail.setTotalPrice(detailDTO.getQuantity() * product.getPrice());

        OrderDetail updated = detailRepository.save(existingDetail);
        return mapToDetailDTO(updated);
    }

    @Override
    public OrderDetailDTO deleteOrderDetail(Integer detailId) {
        Optional<OrderDetail> optionalDetail = detailRepository.findById(detailId);
        if (optionalDetail.isEmpty()) {
            throw new IllegalArgumentException("Order detail không tồn tại!");
        }

        OrderDetail existingDetail = optionalDetail.get();

        // Kiểm tra trạng thái của Order
        OrderDTO orderDTO = orderService.getOrderById(existingDetail.getOrder().getOrderId());
        if (!"PENDING".equalsIgnoreCase(orderDTO.getStatus())) {
            throw new IllegalStateException("Không thể xoá món trong order đã xử lý!");
        }

        detailRepository.delete(existingDetail);
        return mapToDetailDTO(existingDetail);
    }

    private OrderDetailDTO mapToDetailDTO(OrderDetail detail) {
        OrderDetailDTO dto = new OrderDetailDTO();
        dto.setOrderDetailId(detail.getOrderDetailId());
        dto.setOrderId(detail.getOrder().getOrderId());
        dto.setProductId(detail.getProduct().getProductId());
        dto.setOptionNote(detail.getOptionNote());
        dto.setQuantity(detail.getQuantity());
        dto.setTotalPrice(detail.getTotalPrice());
        return dto;
    }

}
