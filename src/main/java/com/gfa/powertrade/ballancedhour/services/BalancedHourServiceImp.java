package com.gfa.powertrade.ballancedhour.services;

import com.gfa.powertrade.ballancedhour.models.BalancedHour;

import com.gfa.powertrade.ballancedhour.repositories.BalancedHourRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor

public class BalancedHourServiceImp implements BalancedHourService {

  public static final Long oneHourInMilliSeconds = 60 * 60 * 1000L;
  private BalancedHourRepository balancedHourRepository;

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
        .balancedHourFromTime(fromTime)
        .balancedHourToTime(toTime)
        .powerQuantityList(new ArrayList<>())
        .demandQuantityList(new ArrayList<>())
        .balancedHourPrice(20d)
        .build();
  }

  public Long calculateNumberOfBallanceHours(Long toTime, Long fromTime) {
    return (toTime - fromTime) / oneHourInMilliSeconds;
  }

}
