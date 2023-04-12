package com.gfa.powertrade.login.models;

import lombok.*;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
  @NotBlank(message = "Username is required.")
  private String username;
  @NotBlank(message = "Password is required.")
  private String password;
  @NotBlank(message = "Usertype is required.")
  private String  userType;


}
