package com.gfa.powertrade.registration.models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponseDTO {

  private int id;
  private String username;
  private UserType userType;

}
