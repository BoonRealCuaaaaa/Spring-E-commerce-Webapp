package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AuthController(UserService userService,RoleService roleService) {
        this.userService=userService;
        this.roleService=roleService;
    }
    @GetMapping("/showLoginPage")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/access-denied")
    public String showAccessDeniedPage() {
        return "access-denied";
    }

    @RequestMapping("/redirectByRole")
    public String redirectByRole(HttpServletRequest request) {

        if(request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/admin";
        }

        return "redirect:/";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("user",new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute("user")User user) {
        user.setPassword("{bcrypt}"+user.getHashPassword());
        user.addRole(roleService.findRoleByName("ROLE_USER"));
        user.setActive(1);
        userService.addUser(user);

        return "redirect:/showLoginPage";
    }

}
