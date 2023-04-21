package com.gfa.powertrade.powerquantity.services;

import com.gfa.powertrade.ballancedhour.models.BalancedHour;
import com.gfa.powertrade.ballancedhour.repositories.BalancedHourRepository;
import com.gfa.powertrade.ballancedhour.services.BalancedHourService;
import com.gfa.powertrade.ballancedhour.services.BalancedHourServiceImp;
import com.gfa.powertrade.capacity.models.Capacity;
import com.gfa.powertrade.capacity.repositories.CapacityRepository;
import com.gfa.powertrade.powerquantity.models.PowerQuantity;
import com.gfa.powertrade.powerquantity.repository.PowerQuantityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PowerQuantityServiceImp implements PowerQuantityService {


  private BalancedHourRepository balancedHourRepository;
  private CapacityRepository capacityRepository;
  private PowerQuantityRepository powerQuantityRepository;
  private BalancedHourService balancedHourService;

  @Override
  public void createPowreQuantities(Capacity capacity) {

    Long numberOfbalancedHours = balancedHourService.calculateNumberOfBallanceHours(capacity);
    Long firstPowerQuantityFromTime = capacity.getCapacityFromTime();
    Long firstPowerQuiantityToTime = firstPowerQuantityFromTime + BalancedHourServiceImp.oneHourInMilliSeconds;
    for (int i = 1; i <= numberOfbalancedHours; i++) {
      PowerQuantity newPowerQuantity = createNewPowerQuantity(capacity, firstPowerQuantityFromTime,
          firstPowerQuiantityToTime);
      BalancedHour balancedHour =
          balancedHourService.findOrCreateIfNotfoundBalancedHour(firstPowerQuantityFromTime, firstPowerQuiantityToTime);
      newPowerQuantity.setBalancedHour(balancedHour);
      balancedHour.getPowerQuantityList().add(newPowerQuantity);
      balancedHourRepository.save(balancedHour);
      powerQuantityRepository.save(newPowerQuantity);
      capacity.getPowerQuantityList().add(newPowerQuantity);
      firstPowerQuantityFromTime += BalancedHourServiceImp.oneHourInMilliSeconds;
      firstPowerQuiantityToTime += BalancedHourServiceImp.oneHourInMilliSeconds;
    }
    capacityRepository.save(capacity);

  }

  public PowerQuantity createNewPowerQuantity(Capacity capacity, Long fromTime, Long toTime) {
    return PowerQuantity.builder()
        .capacity(capacity)
        .powerQuantityAmount(capacity.getCapacityAmount())
        .powerQuantityFromTime(fromTime)
        .powerQuantityToTime(toTime)
        .build();
  }

}
