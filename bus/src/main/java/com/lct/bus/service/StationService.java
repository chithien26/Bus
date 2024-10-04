package com.lct.bus.service;

import com.lct.bus.dto.RouteDTO;
import com.lct.bus.dto.StationDTO;
import com.lct.bus.models.Route;
import com.lct.bus.models.Station;
import com.lct.bus.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StationService {
    @Autowired
    private StationRepository stationRepository;

    public List<Station> getAllStation() {
        return stationRepository.findAll();
    }

    public List<Station> getAllStationWithKw(String kw) {
        return stationRepository.findAllWithKw(kw);
    }

    public Station getStationById(int id) {
        return stationRepository.findById(id).orElse(null);
    }

    public void saveStation(Station station) {
//        Station s = routeRepository.findById(route.getId());

        stationRepository.save(station);
    }

    public void createStation(StationDTO stationDTO) {
        Boolean existsStation = stationRepository.existsById(stationDTO.getId());
        if (existsStation) {
            new RuntimeException("Station đã tồn tại");
        }

        Station s = new Station();

        s.setId(stationDTO.getId());
        s.setName(stationDTO.getName());
        s.setAddress(stationDTO.getAddress());
        s.setLongitude(stationDTO.getLongitude());
        s.setLatitude(stationDTO.getLatitude());
        s.setCreatedDate(LocalDateTime.now());
        s.setActive(true);
        stationRepository.save(s);
    }

    public void updateStation(Station station) {
        Station stationUpdate = stationRepository.findById(station.getId())
                .orElseThrow(() -> new RuntimeException("Station not found"));

        stationUpdate.setName(station.getName());
        stationUpdate.setAddress(station.getAddress());
        stationUpdate.setLongitude(station.getLongitude());
        stationUpdate.setLatitude(station.getLatitude());
        stationUpdate.setActive(station.getActive());

        stationRepository.save(stationUpdate);
    }

    public void deleteStation(int id) {
        stationRepository.deleteById(id);
    }
}
