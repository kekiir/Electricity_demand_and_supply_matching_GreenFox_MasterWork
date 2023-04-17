package com.gfa.powertrade.common.services;

import com.gfa.powertrade.capacity.models.*;
import com.gfa.powertrade.demand.models.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ConverterService {

 private TimeService timeService;

  public DemandResponseDTO convertDemandToResponseDTO(Demand demand) {
    return new DemandResponseDTO(demand.getId(),
        demand.getAmount(), demand.getRemained(), demand.getPrice(),
        timeService.longToLocalDateTime(demand.getTimeRange().getFrom()),
        timeService.longToLocalDateTime(demand.getTimeRange().getTo()));
  }

  public DemandListResponseDTO convertDemandToDemandListDTO(List<Demand> demandList) {
    List<DemandResponseDTO> list = demandList.stream()
        .map(this::convertDemandToResponseDTO)
        .collect(Collectors.toList());

    return new DemandListResponseDTO(list);
  }

  public CapacityResponseDTO convertCapacityToResponseDTO(Capacity capacity) {
    return new CapacityResponseDTO(capacity.getId(), capacity.getEnergySource().toString(),
        capacity.getAmount(), capacity.getAvailable(), capacity.getPrice(),
        timeService.longToLocalDateTime(capacity.getTimeRange().getFrom()),
        timeService.longToLocalDateTime(capacity.getTimeRange().getTo()));
  }

  public CapacityListResponseDTO convertCapacityToCapacityListDTO(List<Capacity> capacityList) {
    List<CapacityResponseDTO> list = capacityList.stream()
        .map(this::convertCapacityToResponseDTO)
        .collect(Collectors.toList());

    return new CapacityListResponseDTO(list);
  }


}
