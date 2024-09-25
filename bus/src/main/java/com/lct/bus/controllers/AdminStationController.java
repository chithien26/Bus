package com.lct.bus.controllers;

import com.lct.bus.dto.StationDTO;
import com.lct.bus.models.Station;
import com.lct.bus.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/station")
public class AdminStationController {
    @Autowired
    private StationService stationService;

    @GetMapping("")
    public String listStations(Model model, @RequestParam(value = "kw", required = false) String kw) {
        model.addAttribute("station", new StationDTO());
        if(kw == null || kw.isEmpty()){
            model.addAttribute("stations", stationService.getAllStation());
        }
        else{
            model.addAttribute("stations", stationService.getAllStationWithKw(kw));
        }
        return "station/stationManage";
    }


    @PostMapping("/add")
    public String createStation(@ModelAttribute(name = "station") StationDTO stationDTO) {
        stationService.createStation(stationDTO);
        return "redirect:/admin/station";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Station station = stationService.getStationById(id);
        model.addAttribute("station", station);
        return "station/stationEdit";
    }

    @PostMapping("/edit/{id}")
    public String updateStation(@PathVariable("id") int id, @ModelAttribute(name = "station") Station station) {
        station.setId(id);
        stationService.updateStation(station);
        return "redirect:/admin/station";
    }

    @GetMapping("/delete/{id}")
    public String deleteStation(@PathVariable("id") int id) {
        stationService.deleteStation(id);
        return "redirect:/admin/station";
    }
}
