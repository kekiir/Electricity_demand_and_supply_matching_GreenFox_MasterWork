package com.gfa.powertrade.demand.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DemandResponseDTO {
  private Integer id;
  private Double amountMW;
  private Double covered;
  private Double price;
  private String fromTime;
  private String toTime;

}
