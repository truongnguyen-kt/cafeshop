package com.kt.cafeshop.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getDetailById(@PathVariable Integer detailId) {
        try {
            OrderDetailDTO detail = orderDetailService.getDetailById(detailId);
            if (detail == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order detail not found with ID: " + detailId);
            }
            return ResponseEntity.ok(detail);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve order detail: " + e.getMessage());
        }
    }

    @GetMapping("/getByOrder/{orderId}")
    public ResponseEntity<?> getDetailsByOrderId(@PathVariable Integer orderId) {
        try {
            List<OrderDetailDTO> details = orderDetailService.getOrderDetailByOrderId(orderId);
            return ResponseEntity.ok(details);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve details: " + e.getMessage());
        }
    }

    @PostMapping("/{tableId}")
    public ResponseEntity<?> createOrderDetail(
            @PathVariable Integer tableId,
            @RequestBody List<OrderDetailDTO> detailDTOs) {
        try {
            List<OrderDetailDTO> createdDetails = orderDetailService.createOrderDetail(tableId, detailDTOs);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDetails);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to create order details: " + e.getMessage());
        }
    }

    @PutMapping("/{detailId}")
    public ResponseEntity<?> updateOrderDetail(@PathVariable Integer detailId, @RequestBody OrderDetailDTO detailDTO) {
        try {
            OrderDetailDTO updated = orderDetailService.updateOrderDetail(detailId, detailDTO);
            if (updated == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order detail not found with ID: " + detailId);
            }
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to update order detail: " + e.getMessage());
        }
    }

    @DeleteMapping("/{detailId}")
    public ResponseEntity<?> deleteOrderDetail(@PathVariable Integer detailId) {
        try {
            OrderDetailDTO deleted = orderDetailService.deleteOrderDetail(detailId);
            if (deleted == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order detail not found with ID: " + detailId);
            }
            return ResponseEntity.ok(deleted);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete order detail: " + e.getMessage());
        }
    }
}