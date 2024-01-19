package com.gfa.powertrade.capacity.services;

import com.gfa.powertrade.capacity.models.*;
import com.gfa.powertrade.capacity.repositories.CapacityRepository;
import com.gfa.powertrade.common.exceptions.*;
import com.gfa.powertrade.common.services.ConverterService;
import com.gfa.powertrade.common.services.TimeServiceImp;
import com.gfa.powertrade.demand.models.DemandListResponseDTO;
import com.gfa.powertrade.demand.repositories.DemandRepository;
import com.gfa.powertrade.powerquantity.repository.PowerQuantityRepository;
import com.gfa.powertrade.powerquantity.services.PowerQuantityService;
import com.gfa.powertrade.supplier.models.Supplier;
import com.gfa.powertrade.supplier.repository.SupplierRepository;
import com.gfa.powertrade.user.models.User;
import com.gfa.powertrade.user.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CapacityServiceImp implements CapacityService {

  private SupplierRepository supplierRepository;
  private TimeServiceImp timeService;
  private CapacityRepository capacityRepository;
  private DemandRepository demandRepository;
  private ConverterService converterService;
  private UserService userService;
  private PowerQuantityService powerQuantityService;
  PowerQuantityRepository powerQuantityRepository;

  public DemandListResponseDTO findDemandsForCapacity(int id, User user) {
    userService.validateSuppliertype(user);
    Long capacityFromTime = capacityRepository.findById(id).get().getCapacityFromTime();
    Long capacityToTime = capacityRepository.findById(id).get().getCapacityToTime();

    return new DemandListResponseDTO(demandRepository.findAll().stream()
        .filter(demand -> demand.getDemandToTime() > capacityFromTime)
        .filter(demand -> demand.getDemandFromTime() < capacityToTime)
        .map(d -> converterService.convertDemandToResponseDTO(d))
        .collect(Collectors.toList()));
  }

  @Override
  public void deleteCapacityById(Integer id, User user) throws IdNotFoundException, IllegalArgumentException,
      ForbiddenActionException {
    Supplier supplier = userService.validateSuppliertype(user);

    Capacity capacity = capacityRepository.findById(id)
        .orElseThrow(() -> new IdNotFoundException());
    capacityBelongsToSupplier(capacity, supplier.getId());

    powerQuantityRepository.deleteInBatch(capacity.getPowerQuantityList());
    capacity.getPowerQuantityList().removeAll(capacity.getPowerQuantityList());
    supplier.getCapacityList().removeIf(c -> c.getId().equals(id));
    supplierRepository.save(supplier);
    capacityRepository.deleteById(id);

  }

  @Override
  public CapacityResponseDTO updateCapacity(CapacityUpdateRequestDTO capacityUpdateRequestDTO, User user) {
    Supplier supplier = userService.validateSuppliertype(user);
    checkCorrectEnergySource(capacityUpdateRequestDTO.getEnergySource());
    Capacity capacity = capacityRepository.findById(capacityUpdateRequestDTO.getId())
        .orElseThrow(() -> new IdNotFoundException());
    capacityBelongsToSupplier(capacity, supplier.getId());
    timeService.validateGivenDates(capacityUpdateRequestDTO.getFromTime(), capacityUpdateRequestDTO.getToTime());
    powerQuantityService.updatePowerQuantities(capacityUpdateRequestDTO, capacity);
    updataCapacity(capacityUpdateRequestDTO, capacity);
    return converterService.convertCapacityToResponseDTO(capacityRepository.save(capacity));
  }

  private void updataCapacity(CapacityUpdateRequestDTO capacityUpdateRequestDTO, Capacity capacity) {
    String capacityFromTimeString = capacityUpdateRequestDTO.getFromTime();
    String capacityToTimeString = capacityUpdateRequestDTO.getToTime();
    capacity.setId(capacityUpdateRequestDTO.getId());
    capacity.setCapacityAmount(capacityUpdateRequestDTO.getAmountMW());
    capacity.setPrice(capacityUpdateRequestDTO.getPrice());
    capacity.setEnergySource(EnergySource.valueOf(capacityUpdateRequestDTO.getEnergySource()));
    capacity.setCapacityFromTime(timeService.stringToLong(capacityFromTimeString));
    capacity.setCapacityToTime(timeService.stringToLong(capacityToTimeString));

  }

  private void capacityBelongsToSupplier(Capacity capacity, Integer userId) throws ForbiddenActionException {
    if (userId != capacity.getSupplier().getId())
      throw new ForbiddenActionException();
  }

  @Override
  public CapacityResponseDTO createCapacity(User user, CapacityRequestDTO capacityRequestDTO) throws
      ForbiddenActionException, IllegalArgumentException, InvalidEnergySourceException {
    userService.validateSuppliertype(user);
    timeService.validateGivenDates(capacityRequestDTO.getFromTime(), capacityRequestDTO.getToTime());
    checkCorrectEnergySource(capacityRequestDTO.getEnergySource());
    Capacity capacity = setCapacityVariables(capacityRequestDTO, user);
    capacityRepository.save(capacity);
    powerQuantityService.createPowerQuantities(capacity, capacity.getCapacityFromTime(), capacity.getCapacityToTime());
    return converterService.convertCapacityToResponseDTO(capacityRepository.save(capacity));
  }

  public Capacity setCapacityVariables(CapacityRequestDTO capacityRequestDTO, User user) {
    Capacity capacity = Capacity.builder()
        .energySource(EnergySource.valueOf(capacityRequestDTO.getEnergySource().toUpperCase()))
        .capacityAmount(capacityRequestDTO.getAmountMW())
        .available(capacityRequestDTO.getAmountMW())
        .price(capacityRequestDTO.getPrice())
        .capacityFromTime(timeService.localDateTimeTolong(LocalDateTime.parse(capacityRequestDTO.getFromTime())))
        .capacityToTime(timeService.localDateTimeTolong(LocalDateTime.parse(capacityRequestDTO.getToTime())))
        .supplier(supplierRepository.findById(user.getId()).get())
        .contractList(new ArrayList<>())
        .powerQuantityList(new ArrayList<>())
        .build();

    return capacity;
  }

  @Override
  public CapacityListResponseDTO getCapacitesBySupplier(User user) {
    Supplier supplier = userService.validateSuppliertype(user);

    return converterService.convertCapacityToCapacityListDTO(supplier.getCapacityList());
  }

  public void checkCorrectEnergySource(String energySource) throws InvalidEnergySourceException {
    try {
      EnergySource.valueOf(energySource);
    } catch (IllegalArgumentException e) {
      throw new InvalidEnergySourceException();
    }
  }

}
