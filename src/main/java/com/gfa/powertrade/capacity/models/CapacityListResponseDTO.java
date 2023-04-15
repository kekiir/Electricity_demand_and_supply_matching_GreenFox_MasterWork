package com.gfa.powertrade.capacity.models;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class CapacityListResponseDTO {
  List<CapacityResponseDTO> capacityResponseDTOList;

  public CapacityListResponseDTO() {
    capacityResponseDTOList = new ArrayList<>();
  }

  public CapacityListResponseDTO(List<CapacityResponseDTO> capacityResponseDTOList) {
    this.capacityResponseDTOList = capacityResponseDTOList;
  }

}
