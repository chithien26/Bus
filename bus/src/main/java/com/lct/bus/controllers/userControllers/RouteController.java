package com.lct.bus.controllers.userControllers;


import com.lct.bus.models.Route;
import com.lct.bus.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/route")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping
    public List<Route> getAllRoutes(@RequestParam(value = "kw", required = false) String kw) {
        if (kw == null || kw.trim().isEmpty()) {
            return routeService.getAllRoutes();
        }
        return routeService.getAllRoutesWithKw(kw);
    }

    @GetMapping("/{id}")
    public Route routeDetail(@PathVariable(value = "id") int id) {
        return routeService.getRouteById(id);
    }
}
