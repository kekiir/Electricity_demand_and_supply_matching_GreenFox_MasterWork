package com.gfa.powertrade.user.models;

import com.gfa.powertrade.registration.models.UserType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

  private Integer id;
  private String username;
  private String password;
  private UserType userType;

}
