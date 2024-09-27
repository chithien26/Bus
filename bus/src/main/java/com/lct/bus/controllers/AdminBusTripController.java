package com.lct.bus.controllers;

import com.lct.bus.dto.BusTripDTO;
import com.lct.bus.models.BusTrip;
import com.lct.bus.service.BusTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/bustrip")
public class AdminBusTripController {
    @Autowired
    private BusTripService busTripService;

    @GetMapping("")
    public String listBusTrips(Model model, @RequestParam(value = "kw", required = false) String kw) {
        model.addAttribute("busTrip", new BusTripDTO());
        if(kw == null || kw.isEmpty()){
            model.addAttribute("busTrips", busTripService.getAllBusTrips());
        }
        else{
            model.addAttribute("busTrips", busTripService.getAllBusTripsWithKw(kw));
        }
        return "busTrip/busTripManage";
    }


    @PostMapping("/add")
    public String createBusTrip(@ModelAttribute BusTripDTO busTripDTO) {
        busTripService.createBusTrip(busTripDTO);
        return "redirect:/admin/bustrip";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        BusTrip busTrip = busTripService.getBusTripById(id);
        model.addAttribute("busTrip", busTrip);
        return "busTrip/busTripEdit";
    }

    @PostMapping("/edit/{id}")
    public String updateBusTrip(@PathVariable("id") int id, @ModelAttribute(name = "busTrip") BusTrip busTrip) {
        busTripService.updateBusTrip(busTrip);
        return "redirect:/admin/bustrip";
    }

    @GetMapping("/delete/{id}")
    public String deleteBusTrip(@PathVariable("id") int id) {
        busTripService.deleteBusTrip(id);
        return "redirect:/admin/bustrip";
    }
}
