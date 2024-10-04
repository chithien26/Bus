package com.lct.bus.service;

import com.lct.bus.dto.ScheduleDTO;
import com.lct.bus.models.Schedule;
import com.lct.bus.models.Station;
import com.lct.bus.repository.ScheduleRepository;
import com.lct.bus.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private StationService stationService;

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getAllSchedulesWithKw(String kw) {
        return scheduleRepository.findAllWithKw(kw);
    }

    public Schedule getScheduleById(int id) {
        return scheduleRepository.findById(id).orElse(null);
    }

    public void saveSchedule(Schedule schedule) {
//        schedule r = scheduleRepository.findById(schedule.getId());

        scheduleRepository.save(schedule);
    }

    public void createSchedule(ScheduleDTO scheduleDTO) {
        Boolean existsschedule = scheduleRepository.existsById(scheduleDTO.getId());
        if (existsschedule) {
            new RuntimeException("schedule đã tồn tại");
        }

        Schedule r = new Schedule();

        r.setId(scheduleDTO.getId());
        r.setStation(scheduleDTO.getStation());
        r.setBusTrip(scheduleDTO.getBusTrip());
        r.setArrivalTime(scheduleDTO.getArrivalTime());
        r.setCreatedDate(LocalDateTime.now());
        r.setActive(true);
        scheduleRepository.save(r);
    }

    public void updateSchedule(Schedule schedule) {
        Schedule scheduleUpdate = scheduleRepository.findById(schedule.getId())
                .orElseThrow(() -> new RuntimeException("schedule not found"));

        scheduleUpdate.setArrivalTime(schedule.getArrivalTime());
        scheduleUpdate.setStation(schedule.getStation());
        scheduleUpdate.setBusTrip(schedule.getBusTrip());
        scheduleUpdate.setActive(schedule.getActive());

        scheduleRepository.save(scheduleUpdate);
    }

    public void deleteSchedule(int id) {
        scheduleRepository.deleteById(id);
    }
}
