package com.lct.bus.controllers;

import com.lct.bus.dto.RouteDTO;
import com.lct.bus.models.Route;
import com.lct.bus.service.RouteService;
import jakarta.persistence.PreUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/route")
public class AdminRouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping("")
    public String listRoutes(Model model, @RequestParam(value = "kw", required = false) String kw) {
        model.addAttribute("route", new RouteDTO());
        if(kw == null || kw.isEmpty()){
            model.addAttribute("routes", routeService.getAllRoutes());
        }
        else{
            model.addAttribute("routes", routeService.getAllRoutesWithKw(kw));
        }
        return "route/routeManage";
    }


    @PostMapping("/add")
    public String createRoute(@ModelAttribute RouteDTO routeDTO) {
        routeService.createRoute(routeDTO);
        return "redirect:/admin/route";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Route route = routeService.getRouteById(id);
        model.addAttribute("route", route);
        return "route/routeEdit";
    }

    @PostMapping("/edit/{id}")
    public String updateroute(@PathVariable("id") int id, @ModelAttribute(name = "route") Route route) {
        routeService.updateRoute(route);
        return "redirect:/admin/route";
    }

    @GetMapping("/delete/{id}")
    public String deleteroute(@PathVariable("id") int id) {
        routeService.deleteRoute(id);
        return "redirect:/admin/route";
    }
}

