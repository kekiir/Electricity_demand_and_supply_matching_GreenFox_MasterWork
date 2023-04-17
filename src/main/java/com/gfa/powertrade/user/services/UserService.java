package com.gfa.powertrade.user.services;

import com.gfa.powertrade.consumers.models.Consumer;
import com.gfa.powertrade.supplier.models.Supplier;
import com.gfa.powertrade.user.models.User;
import java.util.Optional;

public interface UserService {
  Optional<User> findByUsername(String username, String userType);

  Supplier validateSuppliertype(User user);

  Consumer validateConsumertype(User user);

}
