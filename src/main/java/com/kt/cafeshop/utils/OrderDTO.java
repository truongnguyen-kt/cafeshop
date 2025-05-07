package com.kt.cafeshop.utils;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO {
    private Integer orderId;

    private Integer tableId;

    private Double totalPrice;

    private String status;

    private LocalDateTime createdDate;
}
