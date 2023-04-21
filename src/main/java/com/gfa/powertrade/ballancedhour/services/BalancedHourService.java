package com.gfa.powertrade.ballancedhour.services;

import com.gfa.powertrade.ballancedhour.models.BalancedHour;
import com.gfa.powertrade.capacity.models.Capacity;

public interface BalancedHourService {
  public BalancedHour findOrCreateIfNotfoundBalancedHour(Long fromTime, Long toTime);

  public BalancedHour createNewBalancedHour(Long fromTime, Long toTime);

  Long calculateNumberOfBallanceHours(Capacity capacity);

}
