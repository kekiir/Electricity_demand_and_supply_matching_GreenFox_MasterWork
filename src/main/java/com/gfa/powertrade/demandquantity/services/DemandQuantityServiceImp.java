package com.gfa.powertrade.demandquantity.services;

import com.gfa.powertrade.ballancedhour.models.BalancedHour;
import com.gfa.powertrade.ballancedhour.repositories.BalancedHourRepository;
import com.gfa.powertrade.ballancedhour.services.BalancedHourService;
import com.gfa.powertrade.ballancedhour.services.BalancedHourServiceImp;
import com.gfa.powertrade.common.services.TimeService;
import com.gfa.powertrade.demand.models.Demand;
import com.gfa.powertrade.demand.models.DemandUpdateRequestDTO;
import com.gfa.powertrade.demand.repositories.DemandRepository;
import com.gfa.powertrade.demandquantity.models.DemandQuantity;
import com.gfa.powertrade.demandquantity.repositories.DemandQuantityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DemandQuantityServiceImp implements DemandQuantityService {

  private BalancedHourRepository balancedHourRepository;
  private DemandRepository demandRepository;
  private DemandQuantityRepository demandQuantityRepository;
  private BalancedHourService balancedHourService;
  private TimeService timeService;

  @Override
  public void createDemandQuantities(Demand demand, Long fromTime, Long toTime) {
    Long numberOfbalancedHours = balancedHourService.calculateNumberOfBallanceHours(toTime, fromTime);
    Long firstDemandQuantityFromTime = fromTime;
    Long firstDemandQuiantityToTime = firstDemandQuantityFromTime + BalancedHourServiceImp.oneHourInMilliSeconds;
    for (int i = 1; i <= numberOfbalancedHours; i++) {
      DemandQuantity newDemandQuantity = createNewDemandQuantity(demand, firstDemandQuantityFromTime,
          firstDemandQuiantityToTime);
      BalancedHour balancedHour =
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

  @Override
  public void updateDemandQuantites(DemandUpdateRequestDTO demandUpdateRequestDTO, Demand demand) {
    Long updatedDemandFromTime = timeService.stringToLong(demandUpdateRequestDTO.getFromTime());
    Long updatedDemandToTime = timeService.stringToLong(demandUpdateRequestDTO.getToTime());

    if (updatedDemandFromTime < demand.getDemandFromTime())
      createDemandQuantitiesDependingOnFromTime(demand, updatedDemandFromTime);

    if (demand.getDemandToTime() < updatedDemandToTime)
      createDemandQuantitiesDependingOnToTime(demand, updatedDemandToTime);

    if (demand.getDemandFromTime() < updatedDemandFromTime
        || updatedDemandToTime < demand.getDemandToTime()) {
      deleteDemandQuantities(demand, updatedDemandFromTime, updatedDemandToTime);
    }
  }

  private void createDemandQuantitiesDependingOnFromTime(Demand demand, Long updatedDemandFromTime) {
    createDemandQuantities(demand, updatedDemandFromTime, demand.getDemandFromTime());
  }

  @Override
  public void createDemandQuantitiesDependingOnToTime(Demand demand, Long updatedDemandToTime) {
    createDemandQuantities(demand, demand.getDemandToTime(), updatedDemandToTime);

  }

  @Transactional
  public void deleteDemandQuantities(Demand demand, Long updatedDemandFromTime,
      Long updatedDemandToTime) {
    List<DemandQuantity> deletedPowrQuantites = new ArrayList<>();
    for (DemandQuantity pq : demand.getDemandQuantityList()) {
      if (updatedDemandFromTime > pq.getDemandQuantityFromTime()
          || updatedDemandToTime < pq.getDemandQuantityToTime()) {
        deletedPowrQuantites.add(pq);
      }
    }
    demandQuantityRepository.deleteInBatch(deletedPowrQuantites);
    demand.getDemandQuantityList().removeAll(deletedPowrQuantites);
    demandRepository.save(demand);
  }

}
