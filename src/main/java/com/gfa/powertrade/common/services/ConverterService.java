package com.gfa.powertrade.common.services;

import com.gfa.powertrade.capacity.models.*;
import com.gfa.powertrade.demand.models.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ConverterService {

  private TimeServiceImp timeService;

  public DemandResponseDTO convertDemandToResponseDTO(Demand demand) {
    return new DemandResponseDTO(demand.getId(), demand.getDemandAmount(), demand.getRemained(), demand.getPrice(),
        timeService.longToLocalDateTime(demand.getDemandFromTime())
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:00")),
        timeService.longToLocalDateTime(demand.getDemandToTime())
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:00")));
  }

  public DemandListResponseDTO convertDemandToDemandListDTO(List<Demand> demandList) {
    List<DemandResponseDTO> list = demandList.stream().map(this::convertDemandToResponseDTO).collect(
        Collectors.toList());

    return new DemandListResponseDTO(list);
  }

  public CapacityResponseDTO convertCapacityToResponseDTO(Capacity capacity) {
    return new CapacityResponseDTO(capacity.getId(), capacity.getEnergySource().toString(),
        capacity.getCapacityAmount(),
        capacity.getAvailable(), capacity.getPrice(),
        timeService.longToLocalDateTime(capacity.getCapacityFromTime())
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:00")),
        timeService.longToLocalDateTime(capacity.getCapacityToTime())
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:00")));
  }

  public CapacityListResponseDTO convertCapacityToCapacityListDTO(List<Capacity> capacityList) {
    List<CapacityResponseDTO> list = capacityList.stream().map(this::convertCapacityToResponseDTO).collect(
        Collectors.toList());

    return new CapacityListResponseDTO(list);
  }

}
