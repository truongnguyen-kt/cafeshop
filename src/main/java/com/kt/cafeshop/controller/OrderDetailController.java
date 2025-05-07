package com.kt.cafeshop.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.kt.cafeshop.service.OrderDetailService;
import com.kt.cafeshop.utils.OrderDetailDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cafeshop/order_detail")
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @GetMapping("/{detailId}")
    public OrderDetailDTO getDetailById(@PathVariable Integer detailId) {
        return orderDetailService.getDetailById(detailId);
    }

    @GetMapping("/getByOrder/{orderId}")
    public List<OrderDetailDTO> getDetailsByOrderId(@PathVariable Integer orderId) {
        return orderDetailService.getOrderDetailByOrderId(orderId);
    }

    @PostMapping("/{tableId}")
    public OrderDetailDTO createOrderDetail(
            @PathVariable Integer tableId,
            @RequestBody OrderDetailDTO detailDTO) {
        return orderDetailService.createOrderDetail(tableId, detailDTO);
    }

    @PutMapping("/{detailId}")
    public OrderDetailDTO updateOrderDetail(@PathVariable Integer detailId, @RequestBody OrderDetailDTO detailDTO) {
        return orderDetailService.updateOrderDetail(detailId, detailDTO);
    }

    @DeleteMapping("/{detailId}")
    public OrderDetailDTO deleteOrderDetail(@PathVariable Integer detailId) {
        return orderDetailService.deleteOrderDetail(detailId);
    }
}
