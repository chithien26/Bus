package com.lct.bus.controllers;


import com.lct.bus.models.Route;
import com.lct.bus.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping("/route/list")
    public List<Route> getAllRoutes() {
        return routeService.getAllRoutes();
    }

    @GetMapping("/route/{id}")
    public Route routeDetail(@PathVariable(value = "id") int id){
        return routeService.getRouteById(id);
    }
}
