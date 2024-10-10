package com.lct.bus.service;

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

    public void createFavourite(Favourite favourite) {
        Boolean existsFavourite = favouriteRepository.existsById(favourite.getId());
        if (existsFavourite) {
            new RuntimeException("Favourite đã tồn tại");
        }
        favourite.setCreatedDate(LocalDateTime.now());
        favourite.setActive(true);
        favouriteRepository.save(favourite);
    }

    public void updateFavourite(Favourite favourite) {
        Favourite favouriteUpdate = favouriteRepository.findById(favourite.getId())
                .orElseThrow(() -> new RuntimeException("favourite not found"));

        favouriteUpdate.setRoute(favourite.getRoute());
        favouriteUpdate.setUser(favourite.getUser());
        favouriteUpdate.setActive(favourite.getActive());

        favouriteRepository.save(favouriteUpdate);
    }

    public void deleteFavourite(int id) {
        favouriteRepository.deleteById(id);
    }

}
