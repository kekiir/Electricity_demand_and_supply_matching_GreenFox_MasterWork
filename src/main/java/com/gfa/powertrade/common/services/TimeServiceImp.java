package com.gfa.powertrade.common.services;

import com.gfa.powertrade.common.exceptions.ForbiddenActionException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.*;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class TimeServiceImp implements TimeService {

  public Long localDateTimeTolong(LocalDateTime localDateTime) {
    ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
    long millisecondsSinceEpoch = zonedDateTime.toInstant().toEpochMilli();
    return millisecondsSinceEpoch;
  }

  public LocalDateTime longToLocalDateTime(Long time) {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
  }

  public void validateGivenDates(String fromString, String toString) throws IllegalArgumentException,
      ForbiddenActionException {
    LocalDateTime from;
    LocalDateTime to;
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime twoHourBeforeTodayMidnight =
        LocalDateTime.now().withHour(22).withMinute(0).withSecond(0).withNano(0);
    LocalDateTime tomorrowMidnight =
        LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);

    LocalDateTime afterTomorrowMidnight =
        LocalDateTime.now().plusDays(2).withHour(0).withMinute(0).withSecond(0).withNano(0);
    if (now.isAfter(twoHourBeforeTodayMidnight) && now.isBefore(tomorrowMidnight))
      throw new IllegalArgumentException("Dates posting for tomorrow excepted until 22:00");
    from = validateDateFormat(fromString);
    to = validateDateFormat(toString);
    validateDateRange(from, to, tomorrowMidnight, afterTomorrowMidnight);

  }

  public LocalDateTime validateDateFormat(String date) {
    try {
      return LocalDateTime.parse(date);
    } catch (DateTimeException e) {
      throw new IllegalArgumentException("Invalid 'from' date");
    }
  }

  public void validateDateRange(LocalDateTime from, LocalDateTime to, LocalDateTime tomorrowMidnight,
      LocalDateTime afterTomorrowMidnight) {
    String tomorrowDate = LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    if (!from.isAfter(tomorrowMidnight.minusNanos(1)) || !to.isBefore(afterTomorrowMidnight.plusNanos(1))) {
      throw new IllegalArgumentException("Dates accepted only for tomorrow(" + tomorrowDate + ").");
    }
    if (from.isAfter(to)) {
      throw new IllegalArgumentException("'From' date have to be earlier than 'to' date.");
    }
  }

}
