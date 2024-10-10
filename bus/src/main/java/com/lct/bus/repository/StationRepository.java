package com.lct.bus.repository;

import com.lct.bus.models.Route;
import com.lct.bus.models.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface StationRepository extends JpaRepository<Station, Integer> {
    @Query("SELECT s FROM Station s WHERE " +
            "(CAST(s.id AS string) LIKE :kw) OR " +
            "(LOWER(s.name) LIKE LOWER(CONCAT('%', :kw, '%')))")
    List<Station> findAllWithKw(String kw);

    @Query("SELECT s FROM Station s order by s.name")
    List<Station> findAll();

}
