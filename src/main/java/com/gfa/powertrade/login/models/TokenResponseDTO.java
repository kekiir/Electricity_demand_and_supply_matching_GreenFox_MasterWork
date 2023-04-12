package com.gfa.powertrade.login.models;

import lombok.Data;

@Data
public class TokenResponseDTO {

  private String status = "ok";
  private String token;

  public TokenResponseDTO(String jws) {
    this.token = jws;
  }

}
