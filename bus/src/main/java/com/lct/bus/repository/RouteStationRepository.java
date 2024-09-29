package com.lct.bus.repository;

import com.lct.bus.models.RouteStation;
import com.lct.bus.models.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface RouteStationRepository extends JpaRepository<RouteStation, Integer> {
    @Query("SELECT rs FROM RouteStation rs WHERE " +
            "(LOWER(rs.route.name) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(LOWER(rs.station.name) LIKE LOWER(CONCAT('%', :kw, '%')))" )
    List<RouteStation> findAllWithKw(String kw);
}
