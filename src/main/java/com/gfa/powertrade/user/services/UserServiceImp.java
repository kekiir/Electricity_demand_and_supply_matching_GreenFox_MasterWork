package com.gfa.powertrade.user.services;

import com.gfa.powertrade.consumers.repositories.ConsumerRepository;
import com.gfa.powertrade.registration.models.UserType;
import com.gfa.powertrade.supplier.models.Supplier;
import com.gfa.powertrade.supplier.repository.SupplierRepository;
import com.gfa.powertrade.supplier.services.SupplierService;
import com.gfa.powertrade.user.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserService {


  private ConsumerRepository consumerRepository;
  private SupplierRepository supplierRepository;

  @Override
  public Optional<User> findByUsername(String username, String userType) {

    if (userType.toUpperCase().equals(UserType.SUPPLIER.toString())) {
      if (supplierRepository.findByUsername(username).isPresent()) {
        User supplier = supplierRepository.findByUsername(username).get();
        supplier.setUserType(UserType.SUPPLIER);
        return Optional.of(supplier);
      } else return Optional.empty();
    } else {
      if (consumerRepository.findByUsername(username).isPresent()) {
        User consumer = consumerRepository.findByUsername(username).get();
        consumer.setUserType(UserType.CONSUMER);
        return Optional.of(consumer);
      } else return Optional.empty();
    }
  }

}
