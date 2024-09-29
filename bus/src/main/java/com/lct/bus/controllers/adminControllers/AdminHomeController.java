package com.lct.bus.controllers.adminControllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@ControllerAdvice
@RequestMapping("/admin")
public class AdminHomeController {

    @GetMapping("")
    public String index(Model model){
        return "home";
    }
}
