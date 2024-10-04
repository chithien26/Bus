package com.lct.bus.service;

import com.lct.bus.dto.RouteDTO;
import com.lct.bus.models.Route;
import com.lct.bus.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RouteService {
    @Autowired
    private RouteRepository routeRepository;

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    public List<Route> getAllRoutesWithKw(String kw) {
        return routeRepository.findAllWithKw(kw);
    }

    public Route getRouteById(int id) {
        return routeRepository.findById(id).orElse(null);
    }

    public void saveRoute(Route route) {
//        Route r = routeRepository.findById(route.getId());

        routeRepository.save(route);
    }

    public void createRoute(RouteDTO routeDTO) {
        Boolean existsRoute = routeRepository.existsById(routeDTO.getId());
        if (existsRoute) {
            new RuntimeException("Route đã tồn tại");
        }

        Route r = new Route();

        r.setId(routeDTO.getId());
        r.setRouteNumber(routeDTO.getRouteNumber());
        r.setName(routeDTO.getName());
        r.setFirstTrip(routeDTO.getFirstTrip());
        r.setLastTrip(routeDTO.getLastTrip());
        r.setFare(routeDTO.getFare());
        r.setCreatedDate(LocalDateTime.now());
        r.setActive(true);
        routeRepository.save(r);
    }

    public void updateRoute(Route route) {
        Route routeUpdate = routeRepository.findById(route.getId())
                .orElseThrow(() -> new RuntimeException("Route not found"));

        routeUpdate.setRouteNumber(route.getRouteNumber());
        routeUpdate.setName(route.getName());
        routeUpdate.setFirstTrip(route.getFirstTrip());
        routeUpdate.setLastTrip(route.getLastTrip());
        routeUpdate.setFare(route.getFare());
        routeUpdate.setActive(route.getActive());

        routeRepository.save(routeUpdate);
    }

    public void deleteRoute(int id) {
        routeRepository.deleteById(id);
    }
}
