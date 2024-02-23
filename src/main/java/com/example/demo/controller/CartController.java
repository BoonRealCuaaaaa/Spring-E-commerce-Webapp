package com.example.demo.controller;

import com.example.demo.global.GlobalData;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CartController {
    private ProductService productService;
    @Autowired
    public CartController(ProductService productService){
        this.productService=productService;
    }

    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable int id) {
        GlobalData.cart.add(productService.getProductById(id).get());

        return "redirect:/shop";
    }
}
