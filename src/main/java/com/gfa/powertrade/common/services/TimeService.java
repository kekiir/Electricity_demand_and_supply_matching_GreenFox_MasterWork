package com.gfa.powertrade.common.services;

import com.gfa.powertrade.common.exceptions.ForbiddenActionException;
import java.time.LocalDateTime;

public interface TimeService {

  public Long localDateTimeTolong(LocalDateTime localDateTime);

  public LocalDateTime longToLocalDateTime(Long time);

  public void validateGivenDates(String fromString, String toString) throws IllegalArgumentException,
      ForbiddenActionException;

  public LocalDateTime validateDateFormat(String date);

  public void validateDateRange(LocalDateTime from, LocalDateTime to, LocalDateTime afterTomorrowMidnight);

}
