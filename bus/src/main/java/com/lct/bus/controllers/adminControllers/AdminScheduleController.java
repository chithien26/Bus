package com.lct.bus.controllers.adminControllers;

import com.lct.bus.dto.ScheduleDTO;
import com.lct.bus.models.Schedule;
import com.lct.bus.service.BusTripService;
import com.lct.bus.service.ScheduleService;
import com.lct.bus.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/schedule")
public class AdminScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private BusTripService busTripService;

    @Autowired
    private StationService stationService;

    @GetMapping("")
    public String listSchedules(Model model, @RequestParam(value = "kw", required = false) String kw) {
        model.addAttribute("schedule", new ScheduleDTO());
        model.addAttribute("busTrips", busTripService.getAllBusTrips());
        model.addAttribute("stations", stationService.getAllStation());
        if (kw == null || kw.isEmpty()) {
            model.addAttribute("schedules", scheduleService.getAllSchedules());
        } else {
            model.addAttribute("schedules", scheduleService.getAllSchedulesWithKw(kw));
        }
        return "schedule/scheduleManage";
    }


    @PostMapping("/add")
    public String createSchedule(@ModelAttribute ScheduleDTO scheduleDTO) {
        scheduleService.createSchedule(scheduleDTO);
        return "redirect:/admin/schedule";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("stations", stationService.getAllStation());
        model.addAttribute("busTrips", busTripService.getAllBusTrips());

        Schedule schedule = scheduleService.getScheduleById(id);
        model.addAttribute("schedule", schedule);
        return "schedule/scheduleEdit";
    }

    @PostMapping("/edit/{id}")
    public String updateSchedule(@PathVariable("id") int id, @ModelAttribute(name = "schedule") Schedule schedule) {
        scheduleService.updateSchedule(schedule);
        return "redirect:/admin/schedule";
    }

    @GetMapping("/delete/{id}")
    public String deleteSchedule(@PathVariable("id") int id) {
        scheduleService.deleteSchedule(id);
        return "redirect:/admin/schedule";
    }
}
