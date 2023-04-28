package com.gfa.powertrade.common.services;

import com.gfa.powertrade.common.exceptions.ForbiddenActionException;
import java.time.LocalDateTime;

public interface TimeService {

   Long localDateTimeTolong(LocalDateTime localDateTime);

   LocalDateTime longToLocalDateTime(Long time);

   void validateGivenDates(String fromString, String toString) throws IllegalArgumentException,
      ForbiddenActionException;

   LocalDateTime validateDateFormat(String date);

   void validateDateRange(LocalDateTime from, LocalDateTime to, LocalDateTime closeTime, LocalDateTime openTime)
      throws IllegalArgumentException;

   Long StringToLong(String timeString);

}
