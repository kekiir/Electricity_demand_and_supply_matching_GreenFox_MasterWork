package com.gfa.powertrade.powerquantity.services;

import com.gfa.powertrade.ballancedhour.models.BalancedHour;
import com.gfa.powertrade.ballancedhour.repositories.BalancedHourRepository;
import com.gfa.powertrade.capacity.models.Capacity;
import com.gfa.powertrade.capacity.repositories.CapacityRepository;
import com.gfa.powertrade.powerquantity.models.PowerQuantity;
import com.gfa.powertrade.powerquantity.repository.PowerQuantityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PowerQuantityServiceImp implements PowerQuantityService {

  public static final Long oneHourInMilliSeconds = 60 * 60 * 1000l;

  private BalancedHourRepository balancedHourRepository;
  private CapacityRepository capacityRepository;
  private PowerQuantityRepository powerQuantityRepository;

  @Override
  public void createPowreQuantities(Capacity capacity) {

    Long numberOfbalancedHours = calculateNumberOfBallanceHours(capacity);
    Long firstPowerQuantityFromTime = capacity.getCapacityFromTime();
    Long firstPowerQuiantityToTime = firstPowerQuantityFromTime + oneHourInMilliSeconds;
    for (int i = 1; i <= numberOfbalancedHours; i++) {
      PowerQuantity newPowerQuantity = createNewPowerQuantity(capacity, firstPowerQuantityFromTime,
          firstPowerQuiantityToTime);
      BalancedHour balancedHour =
          findOrCreateIfNotfoundBalancedHour(firstPowerQuantityFromTime, firstPowerQuiantityToTime);
      newPowerQuantity.setBalancedHour(balancedHour);
      balancedHour.getPowerQuantityList().add(newPowerQuantity);
      powerQuantityRepository.save(newPowerQuantity);
      balancedHourRepository.save(balancedHour);
      capacity.getPowerQuantityList().add(newPowerQuantity);
      firstPowerQuantityFromTime += oneHourInMilliSeconds;
      firstPowerQuiantityToTime += oneHourInMilliSeconds;
    }
      capacityRepository.save(capacity);

  }

  public BalancedHour findOrCreateIfNotfoundBalancedHour(Long fromTime, Long toTime) {
    Optional<BalancedHour> optionalBalancedHour = balancedHourRepository.findByBalancedHourFromTime(fromTime);
    if (optionalBalancedHour.isPresent()) {
      return optionalBalancedHour.get();
    } else {
      return createNewBalancedHour(fromTime, toTime);
    }
  }

  public PowerQuantity createNewPowerQuantity(Capacity capacity, Long fromTime, Long toTime) {
    return PowerQuantity.builder()
        .capacity(capacity)
        .powerQuantityAmount(capacity.getCapacityAmount())
        .powerQuantityFromTime(fromTime)
        .powerQuantityToTime(toTime)
        .build();
  }

  public BalancedHour createNewBalancedHour(Long fromTime, Long toTime) {
    return BalancedHour.builder()
        .balanced_hour_from_time(fromTime)
        .balanced_hour_to_time(toTime)
        .powerQuantityList(new ArrayList<>())
        .balanced_hour_price(20d)
        .build();
  }

  public Long calculateNumberOfBallanceHours(Capacity capacity) {
    return (capacity.getCapacityToTime() - capacity.getCapacityFromTime()) / oneHourInMilliSeconds;
  }

}
