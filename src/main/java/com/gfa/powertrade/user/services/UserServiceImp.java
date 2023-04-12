package com.gfa.powertrade.user.services;

import com.gfa.powertrade.consumers.repositories.ConsumerRepository;
import com.gfa.powertrade.registration.models.UserType;
import com.gfa.powertrade.supplier.repository.SupplierRepository;
import com.gfa.powertrade.supplier.services.SupplierService;
import com.gfa.powertrade.user.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserService {

  private SupplierService supplierService;
  private ConsumerRepository consumerRepository;
  private SupplierRepository supplierRepository;

  @Override
  public Optional<User> findByUsername(String username, String userType) {

    if (userType.toUpperCase().equals(UserType.SUPPLIER.toString())) {
      if (supplierRepository.findByUsername(username).isPresent()) {
        return Optional.of(supplierRepository.findByUsername(username).get());
      } else return Optional.empty();
    } else {
      if (consumerRepository.findByUsername(username).isPresent()) {
        return Optional.of(consumerRepository.findByUsername(username).get());
      } else return Optional.empty();
    }
  }

}
