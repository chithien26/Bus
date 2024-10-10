package com.lct.bus.repository;

import com.lct.bus.models.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface FavouriteRepository extends JpaRepository<Favourite, Integer> {
    @Query("SELECT f FROM Favourite f WHERE f.user.id = :id")
    List<Favourite> findByUserId(int id);
}
