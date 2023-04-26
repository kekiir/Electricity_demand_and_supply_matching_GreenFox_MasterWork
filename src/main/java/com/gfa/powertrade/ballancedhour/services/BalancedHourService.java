package com.gfa.powertrade.ballancedhour.services;

import com.gfa.powertrade.ballancedhour.models.BalancedHour;

public interface BalancedHourService {
  BalancedHour findOrCreateIfNotfoundBalancedHour(Long fromTime, Long toTime);

  BalancedHour createNewBalancedHour(Long fromTime, Long toTime);

  Long calculateNumberOfBallanceHours(Long toTime, Long fromTime);

}
