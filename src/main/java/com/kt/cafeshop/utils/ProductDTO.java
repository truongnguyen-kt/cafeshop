package com.kt.cafeshop.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Integer productId;
    private String name;
    private Double price;
    private String description;
    private String image;
    private Boolean status;
}
