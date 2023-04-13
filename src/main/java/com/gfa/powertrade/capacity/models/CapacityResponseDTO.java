package com.gfa.powertrade.capacity.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CapacityResponseDTO {

    private Integer id;
    private String energySource;
    private float amountMW;
    private float available;
    private float price;
    private LocalDateTime from;
    private LocalDateTime to;

}
