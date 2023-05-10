package com.gfa.powertrade.demandquantity.services;

import com.gfa.powertrade.demand.models.Demand;
import com.gfa.powertrade.demand.models.DemandUpdateRequestDTO;
import com.gfa.powertrade.demandquantity.models.DemandQuantity;

public interface DemandQuantityService {
  void createDemandQuantities(Demand demand, Long fromTime, Long toTime);

  DemandQuantity createNewDemandQuantity(Demand demand, Long fromTime, Long toTime);

  void updateDemandQuantites(DemandUpdateRequestDTO demandUpdateRequestDTO, Demand demand);

  void createDemandQuantitiesDependingOnToTime(Demand demand, Long updatedDemandToTime);

}
