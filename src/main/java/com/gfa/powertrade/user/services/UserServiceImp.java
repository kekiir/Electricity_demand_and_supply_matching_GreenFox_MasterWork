package com.gfa.powertrade.user.services;

import com.gfa.powertrade.common.exceptions.ForbiddenActionException;
import com.gfa.powertrade.consumers.models.Consumer;
import com.gfa.powertrade.consumers.repositories.ConsumerRepository;
import com.gfa.powertrade.login.exceptions.LoginFailureException;
import com.gfa.powertrade.registration.models.UserType;
import com.gfa.powertrade.supplier.models.Supplier;
import com.gfa.powertrade.supplier.repository.SupplierRepository;
import com.gfa.powertrade.user.models.User;
import lombok.*;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@AllArgsConstructor

public class UserServiceImp implements UserService {

  private SupplierRepository supplierRepository;
  private ConsumerRepository consumerRepository;

  @Override
  public Optional<User> findByUsername(String username, String userType) {

    if (userType.toUpperCase().equals(UserType.SUPPLIER.toString())) {
      if (supplierRepository.findByUsername(username).isPresent()) {
        User supplier = supplierRepository.findByUsername(username).get();
        supplier.setUserType(UserType.SUPPLIER);
        return Optional.of(supplier);
      } else
        throw new LoginFailureException();
    } else {
      if (consumerRepository.findByUsername(username).isPresent()) {
        User consumer = consumerRepository.findByUsername(username).get();
        consumer.setUserType(UserType.CONSUMER);
        return Optional.of(consumer);
      } else
        throw new LoginFailureException();
    }
  }

  public Supplier validateSuppliertype(User user) throws ForbiddenActionException {
    Supplier supplier;
    try {
      return supplier = (Supplier) user;
    } catch (RuntimeException e) {
      throw new ForbiddenActionException();
    }

  }

  public Consumer validateConsumertype(User user) throws ForbiddenActionException {
    Consumer consumer;
    try {
      return consumer = (Consumer) user;
    } catch (RuntimeException e) {
      throw new ForbiddenActionException();
    }
  }

}
