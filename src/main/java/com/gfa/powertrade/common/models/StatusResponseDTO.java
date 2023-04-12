package com.gfa.powertrade.common.models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusResponseDTO {
  private String status;
  private String message;

  public void setStatusOk() {
    this.setStatus("ok");
  }

  public void setStatusError() {
    this.setStatus("error");
  }

}