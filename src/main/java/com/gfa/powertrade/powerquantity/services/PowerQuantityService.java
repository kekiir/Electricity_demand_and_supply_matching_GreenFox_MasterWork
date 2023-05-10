package com.gfa.powertrade.powerquantity.services;

import com.gfa.powertrade.capacity.models.Capacity;
import com.gfa.powertrade.capacity.models.CapacityUpdateRequestDTO;
import com.gfa.powertrade.powerquantity.models.PowerQuantity;

public interface PowerQuantityService {

  void createPowerQuantities(Capacity capacity, Long fromTime, Long toTime);

  PowerQuantity createNewPowerQuantity(Capacity capacity, Long fromTime, Long toTime);

  void updatePowerQuantities(CapacityUpdateRequestDTO capacityUpdateRequestDTO, Capacity capacity);

  void createPowerQuantitiesDependingOnFromTime(Capacity capacity, Long updatedCapacityFromTime);

  void createPowerQuantitiesDependingOnToTime(Capacity capacity, Long updatedCapacityToTime);

  void deletePowerQuantities(Capacity capacity, Long updatedCapacityFromTime,
      Long updatedCapacityToTime);





}