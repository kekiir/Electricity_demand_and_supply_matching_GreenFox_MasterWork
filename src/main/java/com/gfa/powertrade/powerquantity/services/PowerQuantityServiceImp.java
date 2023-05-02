package com.gfa.powertrade.powerquantity.services;

import com.gfa.powertrade.ballancedhour.models.BalancedHour;
import com.gfa.powertrade.ballancedhour.repositories.BalancedHourRepository;
import com.gfa.powertrade.ballancedhour.services.BalancedHourService;
import com.gfa.powertrade.ballancedhour.services.BalancedHourServiceImp;
import com.gfa.powertrade.capacity.models.Capacity;
import com.gfa.powertrade.capacity.models.CapacityUpdateRequestDTO;
import com.gfa.powertrade.capacity.repositories.CapacityRepository;
import com.gfa.powertrade.common.services.TimeService;
import com.gfa.powertrade.powerquantity.models.PowerQuantity;
import com.gfa.powertrade.powerquantity.repository.PowerQuantityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PowerQuantityServiceImp implements PowerQuantityService {

  private BalancedHourRepository balancedHourRepository;
  private CapacityRepository capacityRepository;
  private PowerQuantityRepository powerQuantityRepository;
  private BalancedHourService balancedHourService;
  private TimeService timeService;

  @Override
  public void createPowreQuantities(Capacity capacity, Long fromTime, Long toTime) {

    Long numberOfbalancedHours = balancedHourService.calculateNumberOfBallanceHours(toTime, fromTime);
    Long firstPowerQuantityFromTime = fromTime;
    Long firstPowerQuiantityToTime = firstPowerQuantityFromTime + BalancedHourServiceImp.oneHourInMilliSeconds;
    for (int i = 1; i <= numberOfbalancedHours; i++) {
      PowerQuantity newPowerQuantity = createNewPowerQuantity(capacity, firstPowerQuantityFromTime,
          firstPowerQuiantityToTime);
      BalancedHour balancedHour =
          balancedHourService.findOrCreateIfNotfoundBalancedHour(firstPowerQuantityFromTime, firstPowerQuiantityToTime);
      newPowerQuantity.setBalancedHour(balancedHour);
      balancedHourRepository.save(balancedHour);
      powerQuantityRepository.save(newPowerQuantity);
      balancedHour.getPowerQuantityList().add(newPowerQuantity);
      capacity.getPowerQuantityList().add(newPowerQuantity);
      firstPowerQuantityFromTime += BalancedHourServiceImp.oneHourInMilliSeconds;
      firstPowerQuiantityToTime += BalancedHourServiceImp.oneHourInMilliSeconds;
    }
    capacityRepository.save(capacity);

  }

  @Override
  public PowerQuantity createNewPowerQuantity(Capacity capacity, Long fromTime, Long toTime) {
    return PowerQuantity.builder()
        .capacity(capacity)
        .powerQuantityAmount(capacity.getCapacityAmount())
        .powerQuantityFromTime(fromTime)
        .powerQuantityToTime(toTime)
        .build();
  }

  @Override
  public void updatePowerQuantities(CapacityUpdateRequestDTO capacityUpdateRequestDTO, Capacity capacity) {
    Long updatedCapacityFromTime = timeService.StringToLong(capacityUpdateRequestDTO.getFromTime());
    Long updatedCapacityToTime = timeService.StringToLong(capacityUpdateRequestDTO.getToTime());

    if (updatedCapacityFromTime < capacity.getCapacityFromTime())
      createPowerQuantitiesDependingOnFromTime(capacity, updatedCapacityFromTime);

    if (capacity.getCapacityToTime() < updatedCapacityToTime)
      createPowreQuantitiesDependingOnToTime(capacity, updatedCapacityToTime);

    if (updatedCapacityFromTime < capacity.getCapacityFromTime() || updatedCapacityToTime < capacity.getCapacityToTime()) {
      deletePowerQuantities(capacity, updatedCapacityFromTime, updatedCapacityToTime);
    }

    //updatePowerQuantitiesDependingOnFromTime(capacityUpdateRequestDTO, capacity);
    //updatePowerQuantitiesDependingOnToTime(capacityUpdateRequestDTO, capacity);
  }

  @Override
  public void updatePowerQuantitiesDependingOnFromTime(CapacityUpdateRequestDTO capacityUpdateRequestDTO,
      Capacity capacity) {

    Long updatedCapacityFromTime = timeService.StringToLong(capacityUpdateRequestDTO.getFromTime());
    if (updatedCapacityFromTime < capacity.getCapacityFromTime()) {
      createPowerQuantitiesDependingOnFromTime(capacity, updatedCapacityFromTime);
    } else {
      deletePowerQuantitiesDependingOnFromTime(capacity, updatedCapacityFromTime);
    }

  }

  @Override
  public void createPowerQuantitiesDependingOnFromTime(Capacity capacity, Long updatedCapacityFromTime) {
    createPowreQuantities(capacity, updatedCapacityFromTime, capacity.getCapacityFromTime());

  }

  @Transactional
  public void deletePowerQuantities(Capacity capacity, Long updatedCapacityFromTime,
      Long updatedCapacityToTime) {
    List<PowerQuantity> deletedPowrQuantites = new ArrayList<>();
    for (PowerQuantity pq : capacity.getPowerQuantityList()) {
      if (updatedCapacityFromTime > pq.getPowerQuantityFromTime()
          || updatedCapacityToTime < pq.getPowerQuantityToTime()) {
        deletedPowrQuantites.add(pq);
      }
    }
    powerQuantityRepository.deleteInBatch(deletedPowrQuantites);
    capacity.getPowerQuantityList().removeAll(deletedPowrQuantites);
    capacityRepository.save(capacity);
  }

  @Transactional
  public void deletePowerQuantitiesDependingOnFromTime(Capacity capacity, Long updatedCapacityFromTime) {
    List<PowerQuantity> deletedPowrQuantites = new ArrayList<>();
    for (PowerQuantity pq : capacity.getPowerQuantityList()) {
      if (updatedCapacityFromTime > pq.getPowerQuantityFromTime()) {
        deletedPowrQuantites.add(pq);
      }
    }
    powerQuantityRepository.deleteInBatch(deletedPowrQuantites);
    capacity.getPowerQuantityList().removeAll(deletedPowrQuantites);
    capacityRepository.save(capacity);
  }

  public void updatePowerQuantitiesDependingOnToTime(CapacityUpdateRequestDTO capacityUpdateRequestDTO,
      Capacity capacity) {

    Long updatedCapacityToTime = timeService.StringToLong(capacityUpdateRequestDTO.getToTime());
    if (capacity.getCapacityToTime() < updatedCapacityToTime) {
      createPowreQuantitiesDependingOnToTime(capacity, updatedCapacityToTime);
    } else {
      deletePowreQuantitiesDependingOnToTime(capacity, updatedCapacityToTime);
    }
  }

  @Override
  public void createPowreQuantitiesDependingOnToTime(Capacity capacity, Long updatedCapacityToTime) {
    createPowreQuantities(capacity, capacity.getCapacityToTime(), updatedCapacityToTime);

  }

  @Transactional
  public void deletePowreQuantitiesDependingOnToTime(Capacity capacity, Long updatedCapacityToTime) {
    List<PowerQuantity> deletedPowrQuantites = new ArrayList<>();

    for (PowerQuantity pq : capacity.getPowerQuantityList()) {
      if (updatedCapacityToTime < pq.getPowerQuantityToTime()) {
        deletedPowrQuantites.add(pq);
      }
    }
    powerQuantityRepository.deleteInBatch(deletedPowrQuantites);
    capacity.getPowerQuantityList().removeAll(deletedPowrQuantites);
    capacityRepository.save(capacity);

  }

}
