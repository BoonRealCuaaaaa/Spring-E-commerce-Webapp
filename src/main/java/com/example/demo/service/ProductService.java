package com.example.demo.service;

import com.example.demo.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product>getAllProduct();
    void addProduct(Product product);
    void removeProductById(int id);
    Optional<Product> getProductById(int id);
    List<Product> getAllProductsByCategoryId(int id);
}
