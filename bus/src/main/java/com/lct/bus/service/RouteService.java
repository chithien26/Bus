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

    public List<Route> getAllRoutes(){
        return routeRepository.findAll();
    }

    public List<Route> getAllRoutesWithKw(String kw){
        return routeRepository.findAllWithKw(kw);
    }

    public Route getRouteById(int id) {
        return routeRepository.findById(id).orElse(null);
    }

    public void saveRoute(Route route) {
//        Route r = routeRepository.findById(route.getId());

        routeRepository.save(route);
    }

    public void createRoute(Route route) {
        Boolean existsRoute = routeRepository.existsById(route.getId());
        if(existsRoute){
            new RuntimeException("Route đã tồn tại");
        }

        Route r = new Route();

        r.setId(route.getId());
        r.setName(route.getName());
        r.setFirstTrip(route.getFirstTrip());
        r.setLastTrip(route.getLastTrip());
        r.setFare(route.getFare());
        r.setCreatedDate(LocalDateTime.now());
        r.setActive(true);
        routeRepository.save(r);
    }

    public void updateRoute(RouteDTO routeDTO) {
        Route routeUpdate = routeRepository.findById(routeDTO.getId())
                .orElseThrow(() -> new RuntimeException("Route not found"));

        routeUpdate.setName(routeDTO.getName());
        routeUpdate.setFirstTrip(routeDTO.getFirstTrip());
        routeUpdate.setLastTrip(routeDTO.getLastTrip());
        routeUpdate.setFare(routeDTO.getFare());
        routeUpdate.setActive(routeDTO.getActive());

        routeRepository.save(routeUpdate);
    }

    public void deleteRoute(int id) {
        routeRepository.deleteById(id);
    }
}
