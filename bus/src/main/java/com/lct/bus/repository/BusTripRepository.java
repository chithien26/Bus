package com.lct.bus.repository;

import com.lct.bus.models.BusTrip;
import com.lct.bus.models.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface BusTripRepository extends JpaRepository<BusTrip, Integer> {
    @Query("SELECT b FROM BusTrip b WHERE " +
            "(CAST(b.id AS string) LIKE %:kw%) OR " +
            "(LOWER(b.route.name) LIKE LOWER(CONCAT('%', :kw, '%')))")
//            "(LOWER(b.vehicle.licensePlates) LIKE LOWER(CONCAT('%', :kw, '%')))")
    List<BusTrip> findAllWithKw(String kw);
}
