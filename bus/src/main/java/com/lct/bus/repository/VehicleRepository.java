package com.lct.bus.repository;

import com.lct.bus.models.Station;
import com.lct.bus.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    @Query("SELECT v FROM Vehicle v WHERE " +
            "(CAST(v.id AS string) LIKE %:kw%) OR " +
            "(LOWER(v.licensePlates) LIKE LOWER(CONCAT('%', :kw, '%'))) OR " +
            "(CAST(v.capacity AS string) LIKE %:kw%) ")
    List<Vehicle> findAllWithKw(String kw);
}
