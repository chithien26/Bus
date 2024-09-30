package com.lct.bus.controllers.userControllers;

import com.lct.bus.models.Route;
import com.lct.bus.models.Station;
import com.lct.bus.service.RouteService;
import com.lct.bus.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/station")
public class StationContronller {
    @Autowired
    private StationService stationService;

    @GetMapping("/list")
    public List<Station> getAllStation() {
        return stationService.getAllStation();
    }

    @GetMapping("/{id}")
    public Station stationDetail(@PathVariable(value = "id") int id){
        return stationService.getStationById(id);
    }
}
