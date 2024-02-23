package com.example.demo.controller;

import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/")
public class HomeController {
    private CategoryService categoryService;
    private ProductService productService;

    public HomeController(CategoryService categoryService, ProductService productService) {
        this.categoryService=categoryService;
        this.productService=productService;
    }

    @GetMapping("/")
    public String getHomepage(Model model) {
        return "index";
    }

    @GetMapping("/shop")
    public String getShop(Model model) {
        model.addAttribute("categories",categoryService.getAllCategory());
        model.addAttribute("products",productService.getAllProduct());
        return "shop";
    }

    @GetMapping("/shop/category/{id}")
    public String getShopByCategory(Model model, @PathVariable int id) {
        model.addAttribute("categories",categoryService.getAllCategory());
        model.addAttribute("products",productService.getAllProductsByCategoryId(id));
        return "shop";
    }

    @GetMapping("/shop/viewproduct/{id}")
    public String viewProductById(Model model, @PathVariable int id) {
        model.addAttribute("product",productService.getProductById(id).get());
        return "viewProduct";
    }
}
