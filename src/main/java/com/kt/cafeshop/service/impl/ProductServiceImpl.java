package com.kt.cafeshop.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.cafeshop.entities.Product;
import com.kt.cafeshop.repository.ProductRepository;
import com.kt.cafeshop.service.ProductService;
import com.kt.cafeshop.utils.ProductDTO;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    private ProductDTO mapToDTO(Product product) {
        return new ProductDTO(
                product.getProductId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getImage(),
                product.getStatus());
    }

    @Override
    public Product mapToProductEntity(ProductDTO dto) {
        Product product = new Product();
        product.setProductId(dto.getProductId());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setImage(dto.getImage());
        product.setStatus(dto.getStatus());
        return product;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Integer id) {
        return productRepository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    @Override
    public ProductDTO createProduct(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setImage(dto.getImage());
        product.setPrice(dto.getPrice());
        product.setStatus(dto.getStatus());

        Product savedProduct = productRepository.save(product);
        return mapToDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(Integer id, ProductDTO dto) {
        Product existing = productRepository.findById(id).orElse(null);
        if (existing == null)
            return null;

        existing.setName(dto.getName());
        existing.setPrice(dto.getPrice());
        existing.setDescription(dto.getDescription());
        existing.setImage(dto.getImage());
        existing.setStatus(dto.getStatus());

        return mapToDTO(productRepository.save(existing));
    }

    @Override
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }
}
