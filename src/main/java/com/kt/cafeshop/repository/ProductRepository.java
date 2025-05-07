package com.kt.cafeshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kt.cafeshop.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

}
