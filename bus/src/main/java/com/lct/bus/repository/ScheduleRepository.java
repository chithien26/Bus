package com.lct.bus.repository;

import com.lct.bus.models.Route;
import com.lct.bus.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ScheduleRepository extends JpaRepository<Schedule,Integer> {
    @Query("SELECT s FROM Schedule s WHERE " +
            "(CAST(s.id AS string) LIKE %:kw%) OR " +
            "(LOWER(s.station.name) LIKE LOWER(CONCAT('%', :kw, '%')))")
    List<Schedule> findAllWithKw(String kw);
}
