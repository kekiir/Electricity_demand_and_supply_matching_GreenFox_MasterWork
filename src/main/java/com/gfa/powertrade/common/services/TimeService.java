package com.gfa.powertrade.common.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.*;

@Service
@AllArgsConstructor
public class TimeService {

  public  Long LocalDateTimeTolong (LocalDateTime localDateTime) {
    ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
    long millisecondsSinceEpoch = zonedDateTime.toInstant().toEpochMilli();
    return millisecondsSinceEpoch;
  }

  public LocalDateTime LongToLocalDateTime (Long time) {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
  }

}
