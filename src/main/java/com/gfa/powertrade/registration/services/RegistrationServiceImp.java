package com.gfa.powertrade.registration.services;

import com.gfa.powertrade.common.exceptions.InvalidUserTypeException;
import com.gfa.powertrade.consumers.models.Consumer;
import com.gfa.powertrade.consumers.repositories.ConsumerRepository;
import com.gfa.powertrade.registration.exceptions.AlreadyTakenUsernameException;
import com.gfa.powertrade.registration.exceptions.InvalidPasswordException;
import com.gfa.powertrade.registration.models.*;
import com.gfa.powertrade.supplier.models.Supplier;
import com.gfa.powertrade.supplier.repository.SupplierRepository;
import com.gfa.powertrade.user.models.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class RegistrationServiceImp implements RegistrationService {

  private SupplierRepository supplierRepository;
  private ConsumerRepository consumerRepository;

  @Override
  public RegistrationResponseDTO saveNewUser(RegistrationRequestDTO reg) throws AlreadyTakenUsernameException,
    InvalidPasswordException {
    validateRegistration(reg);
    if (reg.getUserType().toUpperCase().equals(UserType.SUPPLIER.toString())) {
      return saveSupplier(reg);
    } else {
      return saveConsumer(reg);
    }

  }

  public RegistrationResponseDTO saveSupplier(RegistrationRequestDTO reg) {
    Supplier supplier = Supplier.builder().username(reg.getUsername())
      .password(hashPassword(reg.getPassword())).capacityList(new ArrayList<>()).build();
    supplier.setUserType(UserType.SUPPLIER);
    supplierRepository.save(supplier);
    return convert(supplier);
  }

  public RegistrationResponseDTO saveConsumer(RegistrationRequestDTO reg) {
    Consumer consumer = Consumer.builder().username(reg.getUsername())
      .password(hashPassword(reg.getPassword())).demandList(new ArrayList<>()).build();
    consumer.setUserType(UserType.CONSUMER);
    consumerRepository.save(consumer);
    return convert(consumer);
  }

  @Override
  public void validateRegistration(RegistrationRequestDTO reg) throws AlreadyTakenUsernameException,
    InvalidPasswordException, InvalidUserTypeException {

    if (!reg.getUserType().toUpperCase().equals(
      UserType.SUPPLIER.toString()) && !reg.getUserType().toUpperCase().equals(UserType.CONSUMER.toString()))
      throw new InvalidUserTypeException();
    if (supplierRepository.findByUsername(reg.getUsername()).isPresent() || consumerRepository.findByUsername(
      reg.getUsername()).isPresent())
      throw new AlreadyTakenUsernameException("Username is already taken.");
    if (reg.getPassword() == null || reg.getPassword().trim().length() < 8)
      throw new InvalidPasswordException("Password must be at least 8 characters.");

  }

  @Override
  public String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  public RegistrationResponseDTO convert(User user) {
    if (user == null)
      return null;
    RegistrationResponseDTO responseDTO = new RegistrationResponseDTO();
    responseDTO.setId(user.getId());
    responseDTO.setUsername(user.getUsername());
    responseDTO.setUserType(user.getUserType());
    return responseDTO;
  }

}
