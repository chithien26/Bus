package com.lct.bus.repository;

import com.lct.bus.models.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface FavouriteRepository extends JpaRepository<Favourite, Integer> {
}
