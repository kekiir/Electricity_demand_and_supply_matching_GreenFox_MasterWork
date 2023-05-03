package com.gfa.powertrade.capacity.models;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class CapacityListResponseDTO {
  List<CapacityResponseDTO> capacityResponseListDTO;

  public CapacityListResponseDTO() {
    capacityResponseListDTO = new ArrayList<>();
  }

  public CapacityListResponseDTO(List<CapacityResponseDTO> capacityResponseListDTO) {
    this.capacityResponseListDTO = capacityResponseListDTO;
  }

}
