package com.lct.bus.repository;

import com.lct.bus.models.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Repository
@Transactional
public interface RouteRepository extends JpaRepository<Route, Integer> {
    @Query("SELECT r FROM Route r WHERE " +
            "(CAST(r.routeNumber AS string) LIKE %:kw%) OR " +
            "(LOWER(r.name) LIKE LOWER(CONCAT('%', :kw, '%')))")
    List<Route> findAllWithKw(String kw);

    @Query("SELECT rs.route "
            + "FROM RouteStation rs "
            + "WHERE rs.station.id IN (:startStationId, :endStationId) "
            + "GROUP BY rs.route.id "
            + "HAVING COUNT(DISTINCT rs.station.id) = 2")
    List<Route> findRouteByTwoStation(int startStationId, int endStationId);


}
