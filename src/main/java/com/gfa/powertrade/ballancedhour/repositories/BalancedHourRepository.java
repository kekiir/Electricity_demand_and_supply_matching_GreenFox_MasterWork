package com.gfa.powertrade.ballancedhour.repositories;

import com.gfa.powertrade.ballancedhour.models.BalancedHour;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BalancedHourRepository extends CrudRepository<BalancedHour, Integer> {

  @Override
  List<BalancedHour> findAll();

  //  @Query("SELECT bh FROM BalancedHour bh WHERE bh.balanced_hour_from_time = :fromTime")
  //  Optional<BalancedHour> findByBalancedHourFromTime(@Param("fromTime") Long balancedHourFromTime);

  Optional<BalancedHour> findByBalancedHourFromTime(Long balancedHourFromTime);

}
