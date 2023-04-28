package com.gfa.powertrade.powerquantity.services;

import com.gfa.powertrade.capacity.models.Capacity;
import com.gfa.powertrade.capacity.models.CapacityUpdateRequestDTO;
import com.gfa.powertrade.powerquantity.models.PowerQuantity;

public interface PowerQuantityService {

  void createPowreQuantities(Capacity capacity, Long fromTime, Long toTime);

  PowerQuantity createNewPowerQuantity(Capacity capacity, Long fromTime, Long toTime);

  void updatePowerQuantities(CapacityUpdateRequestDTO capacityUpdateRequestDTO, Capacity capacity);

  void createPowreQuantitiesDependingOnToTime(Capacity capacity, Long updatedCapacityToTime);

  void updatePowerQuantitiesDependingOnFromTime(CapacityUpdateRequestDTO capacityUpdateRequestDTO, Capacity capacity);

  public void createPowreQuantitiesDependingOnFromTime(Capacity capacity, Long fromTime);

}