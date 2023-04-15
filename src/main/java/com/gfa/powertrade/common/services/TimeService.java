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

}
