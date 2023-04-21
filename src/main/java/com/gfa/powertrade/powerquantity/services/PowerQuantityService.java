package com.gfa.powertrade.powerquantity.services;

import com.gfa.powertrade.capacity.models.Capacity;
import com.gfa.powertrade.powerquantity.models.PowerQuantity;

public interface PowerQuantityService {

  void createPowreQuantities(Capacity capacity);

  PowerQuantity createNewPowerQuantity(Capacity capacity, Long fromTime, Long toTime);

}