package com.gfa.powertrade.common.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.*;

@Service
@AllArgsConstructor
public class TimeService {

  public  Long localDateTimeTolong(LocalDateTime localDateTime) {
    ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
    long millisecondsSinceEpoch = zonedDateTime.toInstant().toEpochMilli();
    return millisecondsSinceEpoch;
  }

  public LocalDateTime longToLocalDateTime(Long time) {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
  }

  public void validateGivenDates(String fromString, String toString) {
    LocalDateTime from;
    LocalDateTime to;
    LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
    try {
      from = LocalDateTime.parse(fromString);
    } catch (DateTimeException e) {
      throw new IllegalArgumentException("Invalid 'from' date");
    }
    try {
      to = LocalDateTime.parse(toString);
    } catch (DateTimeException e) {
      throw new IllegalArgumentException("Invalid 'to' date");
    }
    if (!from.isAfter(tomorrow) || !to.isAfter(tomorrow)) {
      throw new IllegalArgumentException("Dates accepted from 24h ahead.");
    }
    if (from.isAfter(to)) {
      throw new IllegalArgumentException("'From' date have to be earlier than 'to' date.");
    }
  }

}
