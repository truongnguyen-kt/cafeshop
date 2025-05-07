package com.kt.cafeshop.service;

import java.util.List;

import com.kt.cafeshop.entities.Product;
import com.kt.cafeshop.utils.ProductDTO;

public interface ProductService {
    List<ProductDTO> getAllProducts();

    ProductDTO getProductById(Integer id);

    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(Integer id, ProductDTO productDTO);

    void deleteProduct(Integer id);

    Product mapToProductEntity(ProductDTO dto);
}
