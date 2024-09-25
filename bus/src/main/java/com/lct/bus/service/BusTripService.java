package com.lct.bus.service;

import com.lct.bus.dto.BusTripDTO;
import com.lct.bus.models.BusTrip;
import com.lct.bus.repository.BusTripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BusTripService {
    @Autowired
    private BusTripRepository busTripRepository;

    public List<BusTrip> getAllBusTrips(){
        return busTripRepository.findAll();
    }

    public List<BusTrip> getAllBusTripsWithKw(String kw){
        return busTripRepository.findAllWithKw(kw);
    }

    public BusTrip getBusTripById(int id) {
        return busTripRepository.findById(id).orElse(null);
    }

    public void saveBusTrip(BusTrip busTrip) {
//        busTrip r = busTripRepository.findById(busTrip.getId());

        busTripRepository.save(busTrip);
    }

    public void createBusTrip(BusTripDTO busTripDTO) {
        Boolean existsbusTrip = busTripRepository.existsById(busTripDTO.getId());
        if(existsbusTrip){
            new RuntimeException("Bus Trip đã tồn tại");
        }

        BusTrip r = new BusTrip();

        r.setId(busTripDTO.getId());
        r.setTripNumber(busTripDTO.getTripNumber());
        r.setDepartureTime(busTripDTO.getDepartureTime());
        r.setRoute(busTripDTO.getRoute());
        r.setSchedule(busTripDTO.getSchedule());
        r.setVehicle(busTripDTO.getVehicle());
        r.setCreatedDate(LocalDateTime.now());
        r.setActive(true);
        busTripRepository.save(r);
    }

    public void updateBusTrip(BusTrip busTrip) {
        BusTrip busTripUpdate = busTripRepository.findById(busTrip.getId())
                .orElseThrow(() -> new RuntimeException("Bus Trip not found"));

        busTripUpdate.setTripNumber(busTrip.getTripNumber());
        busTripUpdate.setDepartureTime(busTrip.getDepartureTime());
        busTripUpdate.setRoute(busTrip.getRoute());
        busTripUpdate.setSchedule(busTrip.getSchedule());
        busTripUpdate.setVehicle(busTrip.getVehicle());
        busTripUpdate.setActive(busTrip.getActive());

        busTripRepository.save(busTripUpdate);
    }

    public void deleteBusTrip(int id) {
        busTripRepository.deleteById(id);
    }
}
