package com.lct.bus.controllers;

import com.lct.bus.dto.RouteDTO;
import com.lct.bus.dto.UserDTO;
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

    public enum Role {
        USER,
        ADMIN,
    }

    @GetMapping("")
    public String listUsers(Model model, @RequestParam(value = "username", required = false) String username) {
        if(username == null || username.isEmpty()){
            model.addAttribute("users", userService.getAllUsers());
        }
        else{
            model.addAttribute("users", userService.getAllUserByUsername(username));
        }
        return "user/userManage";
    }

    @GetMapping("/add")
    public String createUser(Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("roles", Role.values());
        return "user/createUser";
    }

    @PostMapping("/add")
    public String createUser(@ModelAttribute UserDTO userDTO) {
        userService.createUser(userDTO);
        return "redirect:/admin/user";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "user/userEdit";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable("id") int id, @ModelAttribute(name = "user") User user) {
//        route.setId(id);
        userService.updateUser(user);
        return "redirect:/admin/user";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin/user";
    }
}

