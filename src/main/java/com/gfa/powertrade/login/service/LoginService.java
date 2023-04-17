package com.gfa.powertrade.login.service;

import com.gfa.powertrade.common.exceptions.InvalidUserTypeException;
import com.gfa.powertrade.login.models.LoginDTO;
import com.gfa.powertrade.login.models.TokenResponseDTO;
import com.gfa.powertrade.user.models.User;

public interface LoginService {

  TokenResponseDTO login(LoginDTO loginDTO);

  public String createToken(User user);

  public void validateUsertype(LoginDTO loginDTO) throws InvalidUserTypeException;

}
