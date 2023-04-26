package com.gfa.powertrade.ballancedhour.services;

import com.gfa.powertrade.ballancedhour.models.BalancedHour;
import com.gfa.powertrade.ballancedhour.repositories.BalancedHourRepository;
import com.gfa.powertrade.capacity.models.Capacity;
import com.gfa.powertrade.demand.models.Demand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor

public class BalancedHourServiceImp implements BalancedHourService{

  private BalancedHourRepository balancedHourRepository;

  public static final Long oneHourInMilliSeconds = 60 * 60 * 1000l;

  @Override
  public BalancedHour findOrCreateIfNotfoundBalancedHour(Long fromTime, Long toTime) {
    Optional<BalancedHour> optionalBalancedHour = balancedHourRepository.findByBalancedHourFromTime(fromTime);
    if (optionalBalancedHour.isPresent()) {
      return optionalBalancedHour.get();
    } else {
      return createNewBalancedHour(fromTime, toTime);
    }
  }


  @Override
  public BalancedHour createNewBalancedHour(Long fromTime, Long toTime) {
      return BalancedHour.builder()
          .balanced_hour_from_time(fromTime)
          .balanced_hour_to_time(toTime)
          .powerQuantityList(new ArrayList<>())
          .demandQuantityList(new ArrayList<>())
          .balanced_hour_price(20d)
          .build();
  }

  public Long calculateNumberOfBallanceHours(Long toTime, Long fromTime) {
    return (toTime - fromTime) / oneHourInMilliSeconds;
  }

}
