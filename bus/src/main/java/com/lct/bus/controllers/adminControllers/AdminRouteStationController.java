package com.lct.bus.controllers.adminControllers;

import com.lct.bus.dto.RouteStationDTO;
import com.lct.bus.models.RouteStation;
import com.lct.bus.service.RouteService;
import com.lct.bus.service.RouteStationService;
import com.lct.bus.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/route-station")
public class AdminRouteStationController {
    @Autowired
    private RouteStationService routeStationService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private StationService stationService;

    @GetMapping("")
    public String listRouteStations(Model model, @RequestParam(value = "kw", required = false) String kw) {
        model.addAttribute("routeStation", new RouteStationDTO());
        model.addAttribute("routes", routeService.getAllRoutes());
        model.addAttribute("stations", stationService.getAllStation());
        if(kw == null || kw.isEmpty()){
            model.addAttribute("routeStations", routeStationService.getAllRouteStation());
        }
        else{
            model.addAttribute("routeStations", routeStationService.getAllRouteStationWithKw(kw));
        }
        return "routeStation/routeStationManage";
    }


    @PostMapping("/add")
    public String createRouteStation(@ModelAttribute RouteStationDTO routeStationDTO) {
        routeStationService.createRouteStation(routeStationDTO);
        return "redirect:/admin/route-station";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("routes", routeService.getAllRoutes());
        model.addAttribute("stations", stationService.getAllStation());
        RouteStation routeStation = routeStationService.getRouteStationById(id);
        model.addAttribute("routeStation", routeStation);
        return "routeStation/routeStationEdit";
    }

    @PostMapping("/edit/{id}")
    public String updateRouteStation(@PathVariable("id") int id, @ModelAttribute(name = "routeStation") RouteStation routeStation) {
        routeStationService.updateRouteStation(routeStation);
        return "redirect:/admin/route-station";
    }

    @GetMapping("/delete/{id}")
    public String deleteRouteStation(@PathVariable("id") int id) {
        routeStationService.deleteRouteStation(id);
        return "redirect:/admin/route-station";
    }
}
