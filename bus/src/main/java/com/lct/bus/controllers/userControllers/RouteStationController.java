package com.lct.bus.controllers.userControllers;

import com.lct.bus.models.RouteStation;
import com.lct.bus.service.RouteStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/route-station")
public class RouteStationController {
    @Autowired
    private RouteStationService routeStationService;


}
