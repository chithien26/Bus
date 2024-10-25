package com.lct.bus.service;

import com.lct.bus.dto.FavouriteDTO;
import com.lct.bus.dto.RouteDTO;
import com.lct.bus.models.Favourite;
import com.lct.bus.models.Route;
import com.lct.bus.repository.FavouriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FavouriteService {
    @Autowired
    private FavouriteRepository favouriteRepository;

    public List<Favourite> getAllFavourite() {
        return favouriteRepository.findAll();
    }


    public List<Favourite> getFavouriteByUserId(int id) {

        return favouriteRepository.findByUserId(id);
    }

    public void saveFavourite(Favourite favourite) {
        favouriteRepository.save(favourite);
    }

    public void createFavourite(FavouriteDTO favouriteDTO) {
        Boolean existsFavourite = favouriteRepository.existsById(favouriteDTO.getId());
        if (existsFavourite) {
            new RuntimeException("Favourite đã tồn tại");
        }

        Favourite favourite = new Favourite();
        favourite.setId(favouriteDTO.getId());
        favourite.setUser(favouriteDTO.getUser());
        favourite.setRoute(favouriteDTO.getRoute());
        favourite.setStartStation(favouriteDTO.getStartStation());
        favourite.setEndStation(favouriteDTO.getEndStation());
        favourite.setCreatedDate(LocalDateTime.now());
        favourite.setActive(true);
        favouriteRepository.save(favourite);
    }

    public void updateFavourite(Favourite favourite) {
        Favourite favouriteUpdate = favouriteRepository.findById(favourite.getId())
                .orElseThrow(() -> new RuntimeException("favourite not found"));

        favouriteUpdate.setRoute(favourite.getRoute());
        favouriteUpdate.setUser(favourite.getUser());
        favourite.setStartStation(favourite.getStartStation());
        favourite.setEndStation(favourite.getEndStation());
        favouriteUpdate.setActive(favourite.getActive());

        favouriteRepository.save(favouriteUpdate);
    }

    public void deleteFavourite(int id) {
        favouriteRepository.deleteById(id);
    }

}
