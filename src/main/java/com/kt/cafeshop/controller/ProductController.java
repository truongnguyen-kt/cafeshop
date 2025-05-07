package com.kt.cafeshop.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.kt.cafeshop.service.ProductService;
import com.kt.cafeshop.utils.ProductDTO;

@RestController
@RequestMapping("/cafeshop/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getAll")
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDTO getById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    @PostMapping("/create")
    public ProductDTO create(@RequestBody ProductDTO dto) {
        return productService.createProduct(dto);
    }

    @PutMapping("/update/{id}")
    public ProductDTO update(@PathVariable Integer id, @RequestBody ProductDTO dto) {
        return productService.updateProduct(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        productService.deleteProduct(id);
    }
}