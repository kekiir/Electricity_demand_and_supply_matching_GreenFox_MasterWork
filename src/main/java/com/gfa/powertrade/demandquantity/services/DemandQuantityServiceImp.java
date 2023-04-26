package com.gfa.powertrade.demandquantity.services;

import com.gfa.powertrade.ballancedhour.models.BalancedHour;
import com.gfa.powertrade.ballancedhour.repositories.BalancedHourRepository;
import com.gfa.powertrade.ballancedhour.services.BalancedHourService;
import com.gfa.powertrade.ballancedhour.services.BalancedHourServiceImp;
import com.gfa.powertrade.demand.models.Demand;
import com.gfa.powertrade.demand.repositories.DemandRepository;
import com.gfa.powertrade.demandquantity.models.DemandQuantity;
import com.gfa.powertrade.demandquantity.repositories.DemandQuantityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DemandQuantityServiceImp implements DemandQuantityService {

  private BalancedHourRepository balancedHourRepository;
  private DemandRepository demandRepository;
  private DemandQuantityRepository demandQuantityRepository;
  private BalancedHourService balancedHourService;

  @Override
  public void createDemandQuantities(Demand demand) {
    Long numberOfbalancedHours = balancedHourService.calculateNumberOfBallanceHours(demand.getDemandToTime(),
        demand.getDemandFromTime());
    Long firstDemandQuantityFromTime = demand.getDemandFromTime();
    Long firstDemandQuiantityToTime = firstDemandQuantityFromTime + BalancedHourServiceImp.oneHourInMilliSeconds;
    for (int i = 1; i <= numberOfbalancedHours; i++) {
      DemandQuantity newDemandQuantity = createNewDemandQuantity(demand, firstDemandQuantityFromTime,
          firstDemandQuiantityToTime);
      BalancedHour balancedHour  =
          balancedHourService.findOrCreateIfNotfoundBalancedHour(firstDemandQuantityFromTime,
              firstDemandQuiantityToTime);
      newDemandQuantity.setBalancedHour(balancedHour);
      balancedHourRepository.save(balancedHour);
      demandQuantityRepository.save(newDemandQuantity);
      balancedHour.getDemandQuantityList().add(newDemandQuantity);
      demand.getDemandQuantityList().add(newDemandQuantity);
      firstDemandQuantityFromTime += BalancedHourServiceImp.oneHourInMilliSeconds;
      firstDemandQuiantityToTime += BalancedHourServiceImp.oneHourInMilliSeconds;
    }
    demandRepository.save(demand);

  }

  @Override
  public DemandQuantity createNewDemandQuantity(Demand demand, Long fromTime, Long toTime) {
    return DemandQuantity.builder()
        .demand(demand)
        .demandQuantityAmount(demand.getDemandAmount())
        .demandQuantityFromTime(fromTime)
        .demandQuantityToTime(toTime)
        .build();
  }

}
