package com.gfa.powertrade.capacity.models;

import lombok.Data;
import javax.validation.constraints.*;

@Data
public class CapacityRequestDTO {

  @NotBlank(message = "energySource: Energy source is required.")
  private String energySource;
  @NotNull(message = "amountMW: Amount is required.")
  @DecimalMin(value = "0.001", message = "amountMW: Amount have to be more then 0.001 MW.")
  private Double amountMW;
  @NotNull(message = "Price is required.")
  private Double price;

  @NotBlank(message = "from: 'From' date is requered.")
  @Pattern(regexp = "^[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0]{2}$", message = "from: Required date pattern: "
      + "yyyy-MM-dd'T'HH:00")
  private String from;

  @NotBlank(message = "to: 'To' date is requered.")
  @Pattern(regexp = "^[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0]{2}$", message = "to: Required date pattern: "
      + "yyyy-MM-dd'T'HH:00")
  private String to;

}
