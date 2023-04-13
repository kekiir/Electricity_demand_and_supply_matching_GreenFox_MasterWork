package com.gfa.powertrade.capacity.models;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
public class CapacityRequestDTO {
  @NotBlank(message = "Energy source is required.")
  @NotNull(message = "Energy source cannot be null.")
  private String energySource;
  @NotNull(message = "Amount is required.")
  @Min(0)
  private float amountMW;
  @NotNull(message = "Price is required.")
  private float price;

  @NotNull(message = "'From' date is requered.")
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH")
  @Future(message = "Date must be in the future")
  private LocalDateTime from;

  @NotNull(message = "'To' date is requered.")
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH")
  @Future(message = "Date must be in the future")
  private LocalDateTime to;

}
