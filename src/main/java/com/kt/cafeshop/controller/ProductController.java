package com.kt.cafeshop.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.kt.cafeshop.service.ProductService;
import com.kt.cafeshop.utils.ProductDTO;

@RestController
@RequestMapping("/cafeshop/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getAll")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        try {
            List<ProductDTO> products = productService.getAllProducts();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            ProductDTO product = productService.getProductById(id);
            if (product == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found with ID: " + id);
            }
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(
            @RequestParam("name") String name,
            @RequestParam("price") double price,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile file,
            @RequestParam("status") Boolean status) {
        try {
            ProductDTO dto = new ProductDTO();
            dto.setName(name);
            dto.setPrice(price);
            dto.setDescription(description);
            dto.setStatus(status);

            String contentType = file.getContentType();
            if (contentType != null &&
                    (contentType.equals(MediaType.IMAGE_JPEG_VALUE) || contentType.equals(MediaType.IMAGE_PNG_VALUE))) {
                dto.setImage(file.getBytes());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid image format (only JPG, PNG allowed).");
            }

            ProductDTO created = productService.createProduct(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("File processing error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating product: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ProductDTO dto) {
        try {
            ProductDTO updated = productService.updateProduct(id, dto);
            if (updated == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found with ID: " + id);
            }
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating product: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Product deleted successfully with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting product: " + e.getMessage());
        }
    }
}