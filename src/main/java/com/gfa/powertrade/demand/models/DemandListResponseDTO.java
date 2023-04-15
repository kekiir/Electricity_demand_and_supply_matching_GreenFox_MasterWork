package com.gfa.powertrade.demand.models;

import com.gfa.powertrade.capacity.models.CapacityResponseDTO;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DemandListResponseDTO {
  List<DemandResponseDTO> demandResponseDTOList;

  public DemandListResponseDTO() {
    demandResponseDTOList = new ArrayList<>();
  }

  public DemandListResponseDTO(List<DemandResponseDTO> demandResponseDTOList) {
    this.demandResponseDTOList = demandResponseDTOList;
  }

}

