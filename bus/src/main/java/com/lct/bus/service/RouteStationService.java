package com.lct.bus.service;

import com.lct.bus.dto.RouteStationDTO;
import com.lct.bus.models.RouteStation;
import com.lct.bus.repository.RouteStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RouteStationService {
    @Autowired
    private RouteStationRepository routeStationRepository;

    public List<RouteStation> getAllRouteStation() {
        return routeStationRepository.findAll();
    }


    public List<RouteStation> getAllRouteStationWithKw(String kw) {
        return routeStationRepository.findAllWithKw(kw);
    }

    public RouteStation getRouteStationById(int id) {
        return routeStationRepository.findById(id).orElse(null);
    }

    public void saveRouteStation(RouteStation routeStation) {
//        routeStation s = routeRepository.findById(route.getId());

        routeStationRepository.save(routeStation);
    }

    public void createRouteStation(RouteStationDTO routeStationDTO) {
        Boolean existsrouteStation = routeStationRepository.existsById(routeStationDTO.getId());
        if (existsrouteStation) {
            new RuntimeException("Route-Station đã tồn tại");
        }

        RouteStation s = new RouteStation();

        s.setId(routeStationDTO.getId());
        s.setRoute(routeStationDTO.getRoute());
        s.setStation(routeStationDTO.getStation());
        s.setOrder(routeStationDTO.getOrder());
        s.setDistToNext(routeStationDTO.getDistToNext());
        s.setCreatedDate(LocalDateTime.now());
        s.setActive(true);
        routeStationRepository.save(s);
    }

    public void updateRouteStation(RouteStation routeStation) {
        RouteStation routeStationUpdate = routeStationRepository.findById(routeStation.getId())
                .orElseThrow(() -> new RuntimeException("Route-Station not found"));

        routeStationUpdate.setRoute(routeStation.getRoute());
        routeStationUpdate.setStation(routeStation.getStation());
        routeStationUpdate.setOrder(routeStation.getOrder());
        routeStationUpdate.setDistToNext(routeStation.getDistToNext());
        routeStationUpdate.setActive(routeStation.getActive());

        routeStationRepository.save(routeStationUpdate);
    }

    public void deleteRouteStation(int id) {
        routeStationRepository.deleteById(id);
    }
}
