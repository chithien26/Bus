package com.lct.bus.controllers;

import com.lct.bus.models.User;
import com.lct.bus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/user")
public class AdminUserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "user/form";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user/form";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable("id") int id, @ModelAttribute User user) {
        user.setId(id);
        userService.saveUser(user);
        return "redirect:/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/list";
    }
}
