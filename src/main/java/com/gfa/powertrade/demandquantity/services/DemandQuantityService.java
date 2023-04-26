package com.gfa.powertrade.demandquantity.services;

import com.gfa.powertrade.demand.models.Demand;
import com.gfa.powertrade.demandquantity.models.DemandQuantity;

public interface DemandQuantityService {
  void createDemandQuantities(Demand demand);
  DemandQuantity createNewDemandQuantity(Demand demand, Long fromTime, Long toTime);
}
