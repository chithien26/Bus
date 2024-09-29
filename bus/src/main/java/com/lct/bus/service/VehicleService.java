package com.lct.bus.service;

import com.lct.bus.dto.VehicleDTO;
import com.lct.bus.models.Vehicle;
import com.lct.bus.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    public List<Vehicle> getAllVehicle(){
        return vehicleRepository.findAll();
    }

    public List<Vehicle> getAllVehicleWithKw(String kw){
        return vehicleRepository.findAllWithKw(kw);
    }

    public Vehicle getVehicleById(int id) {
        return vehicleRepository.findById(id).orElse(null);
    }

    public void saveVehicle(Vehicle vehicle) {
//        vehicle s = routeRepository.findById(route.getId());

        vehicleRepository.save(vehicle);
    }

    public void createVehicle(VehicleDTO vehicleDTO) {
        Boolean existsvehicle = vehicleRepository.existsById(vehicleDTO.getId());
        if(existsvehicle){
            new RuntimeException("vehicle đã tồn tại");
        }

        Vehicle s = new Vehicle();

        s.setLicensePlates(vehicleDTO.getLicensePlates());
        s.setCapacity(vehicleDTO.getCapacity());
        s.setCreatedDate(LocalDateTime.now());
        s.setActive(true);
        vehicleRepository.save(s);
    }

    public void updatevehicle(Vehicle vehicle) {
        Vehicle vehicleUpdate = vehicleRepository.findById(vehicle.getId())
                .orElseThrow(() -> new RuntimeException("vehicle not found"));

        vehicleUpdate.setLicensePlates(vehicle.getLicensePlates());
        vehicleUpdate.setCapacity(vehicle.getCapacity());
        vehicleUpdate.setActive(vehicle.getActive());

        vehicleRepository.save(vehicleUpdate);
    }

    public void deleteVehicle(int id) {
        vehicleRepository.deleteById(id);
    }
}
