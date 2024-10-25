package com.lct.bus.controllers.userControllers;

import com.lct.bus.models.RouteStation;
import com.lct.bus.service.RouteStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/route-station")
public class RouteStationController {
    @Autowired
    private RouteStationService routeStationService;

    @GetMapping("/route/{routeId}")
    public List<RouteStation> getRouteStationByRouteId(@PathVariable(value = "routeId") int id){
        return routeStationService.getByRouteId(id);
    }

    @GetMapping("/get-route-station-in-route")
    public List<RouteStation> getRouteStationInRoute(
            @RequestParam(value = "routeId") int routeId,
            @RequestParam(value = "startStationId") int startStationId,
            @RequestParam(value = "endStationId") int endStationId){
        return routeStationService.getByRouteAndStationOrder(routeId, startStationId, endStationId);
    }


}
