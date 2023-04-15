package com.gfa.powertrade.capacity.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CapacityUpdatedResponseDTO {

  private Integer id;
  private String energySource;
  private Double amountMW;
  private Double available;
  private Double price;
  private LocalDateTime from;
  private LocalDateTime to;

}
