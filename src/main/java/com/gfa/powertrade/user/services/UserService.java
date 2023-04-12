package com.gfa.powertrade.user.services;

import com.gfa.powertrade.user.models.User;
import java.util.Optional;

public interface UserService {
  public Optional<User> findByUsername(String username, String userType);

}
