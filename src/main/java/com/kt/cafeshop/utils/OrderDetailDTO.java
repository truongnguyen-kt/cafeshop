package com.kt.cafeshop.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDetailDTO {
    private Integer orderDetailId;

    private Integer orderId;

    private Integer productId;

    private String optionNote;

    private Integer quantity;

    private Double totalPrice;
}
