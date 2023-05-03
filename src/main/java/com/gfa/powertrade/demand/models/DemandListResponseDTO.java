package com.gfa.powertrade.demand.models;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DemandListResponseDTO {
  List<DemandResponseDTO> demandResponseListDTO;

  public DemandListResponseDTO() {
    demandResponseListDTO = new ArrayList<>();
  }

  public DemandListResponseDTO(List<DemandResponseDTO> demandResponseListDTO) {
    this.demandResponseListDTO = demandResponseListDTO;
  }

}

