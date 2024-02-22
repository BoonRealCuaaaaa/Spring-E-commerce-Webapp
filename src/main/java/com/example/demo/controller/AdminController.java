package com.example.demo.controller;

import com.example.demo.dto.ProductDTO;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
    private CategoryService categoryService;
    private ProductService productService;

    @Autowired
    public AdminController(CategoryService categoryService, ProductService productService) {
        this.categoryService=categoryService;
        this.productService=productService;
    }

    @GetMapping("")
    public String adminHome() {
        return "adminHome";
    }

    @GetMapping("/categories")
    public String getCate(Model model) {
        model.addAttribute("categories",categoryService.getAllCategory());
        return "categories";
    }

    @GetMapping("/categories/add")
    public String getCateAdd(Model model) {
        model.addAttribute("category",new Category());
        return "categoriesAdd";
    }

    @PostMapping("/categories/add")
    public String postCateAdd(@ModelAttribute("category")Category category) {
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCate(@PathVariable int id) {
        categoryService.removeCategoryById(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/update/{id}")
    public String updateCate(@PathVariable int id, Model model) {
        Optional<Category> category=categoryService.getCategoryById(id);

        if(category.isPresent()) {
            model.addAttribute("category",category.get());
            return "categoriesAdd";
        } else {
            return "404";
        }
    }

    //Product section
    @GetMapping("/products")
    public String getALlProducts(Model model) {
        model.addAttribute("products",productService.getAllProduct());
        return "products";
    }

    @GetMapping("/products/add")
    public String getProductAdd(Model model) {
        model.addAttribute("productDTO",new ProductDTO());
        model.addAttribute("categories",categoryService.getAllCategory());
        return "productsAdd";
    }

    @PostMapping("/products/add")
    public String productAddPost(@ModelAttribute("productDTO")ProductDTO productDTO,
                                 @RequestParam("productImage")MultipartFile file,
                                 @RequestParam("imgName")String imgName) throws IOException {

        Product product=new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());

        String imageUUID;

        if(!file.isEmpty()) {
            imageUUID=file.getOriginalFilename();
            Path fileNameAndPath= Paths.get(uploadDir,imageUUID);
            Files.write(fileNameAndPath,file.getBytes());
        } else {
            imageUUID=imgName;
        }

        product.setImageName(imageUUID);
        productService.addProduct(product);

        return "redirect:/admin/products";
    }
}
