package com.gfa.powertrade.capacity.services;

import com.gfa.powertrade.capacity.models.*;
import com.gfa.powertrade.capacity.repositories.CapacityRepository;
import com.gfa.powertrade.common.exceptions.*;
import com.gfa.powertrade.common.models.TimeRange;
import com.gfa.powertrade.common.models.TimeRangeRepository;
import com.gfa.powertrade.common.services.TimeService;
import com.gfa.powertrade.registration.models.UserType;
import com.gfa.powertrade.supplier.models.Supplier;
import com.gfa.powertrade.supplier.repository.SupplierRepository;
import com.gfa.powertrade.user.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class CapacityServiceImp implements CapacityService {

  private SupplierRepository supplierRepository;
  private TimeService timeService;
  private CapacityRepository capacityRepository;
  private TimeRangeRepository timeRangeRepository;

  @Override
  public void deleteCapacityById(Integer id, User user) throws IdNotFoundException, IllegalArgumentException, ForbiddenActionException {
  Supplier supplier = validateUsertype(user);

    Capacity capacity = capacityRepository.findById(id)
        .orElseThrow(() -> new IdNotFoundException());
    capacityBelongsToSupplier(capacity, supplier.getId());
    capacityRepository.delete(capacity);

  }

  @Override
  public CapacityUpdatedResponseDTO updateCapacityById(CapacityUpdateRequestDTO capacityUpdateRequestDTO, User user) {
    Supplier supplier = validateUsertype(user);
    checkCorrectEnergySource(capacityUpdateRequestDTO.getEnergySource());
    Capacity capacity = capacityRepository.findById(capacityUpdateRequestDTO.getId())
        .orElseThrow(() -> new IdNotFoundException());
    capacityBelongsToSupplier(capacity, supplier.getId());
    validateGivenDates(capacityUpdateRequestDTO.getFrom(),capacityUpdateRequestDTO.getTo());
    updataCapacity(capacityUpdateRequestDTO, capacity);
    return convertTOUpdatedResponseDTO(capacityRepository.save(capacity));
  }

  private void updataCapacity(CapacityUpdateRequestDTO capacityUpdateRequestDTO, Capacity capacity) {
    capacity.setId(capacityUpdateRequestDTO.getId());
    capacity.setAmount(capacityUpdateRequestDTO.getAmountMW());
    capacity.setPrice(capacityUpdateRequestDTO.getPrice());
    capacity.setEnergySource(EnergySource.valueOf(capacityUpdateRequestDTO.getEnergySource()));
    updateTimeRange(capacityUpdateRequestDTO, capacity);

  }

  public void updateTimeRange(CapacityUpdateRequestDTO capacityUpdateRequestDTO, Capacity capacity) {
    String from = capacityUpdateRequestDTO.getFrom();
    String to = capacityUpdateRequestDTO.getTo();
    capacity.getTimeRange().setFrom(timeService.localDateTimeTolong(LocalDateTime.parse(from)));
    capacity.getTimeRange().setTo(timeService.localDateTimeTolong(LocalDateTime.parse(to)));
    timeRangeRepository.save(capacity.getTimeRange());
  }

  private CapacityUpdatedResponseDTO convertTOUpdatedResponseDTO(Capacity capacity) {
    return new CapacityUpdatedResponseDTO(capacity.getId(), capacity.getEnergySource().toString(),
        capacity.getAmount(), capacity.getAvailable(), capacity.getPrice(),
        timeService.longToLocalDateTime(capacity.getTimeRange().getFrom()),
        timeService.longToLocalDateTime(capacity.getTimeRange().getTo()));
  }


  private void capacityBelongsToSupplier(Capacity capacity, Integer userId) throws ForbiddenActionException {
    if (userId != capacity.getSupplier().getId())
      throw new ForbiddenActionException();
  }

  @Override
  public CapacityResponseDTO createCapacity(User user, CapacityRequestDTO capacityRequestDTO) throws
      ForbiddenActionException, IllegalArgumentException, InvalidEnergySourceException {
    validateUsertype(user);
    validateGivenDates(capacityRequestDTO.getFrom(),capacityRequestDTO.getTo());
    checkCorrectEnergySource(capacityRequestDTO.getEnergySource());
    Capacity capacity = setCapacityVariables(capacityRequestDTO, user);

    return convert(capacityRepository.save(capacity));
  }

  public Capacity setCapacityVariables(CapacityRequestDTO capacityRequestDTO, User user) {

    Capacity capacity = Capacity.builder()
        .energySource(EnergySource.valueOf(capacityRequestDTO.getEnergySource().toUpperCase()))
        .amount(capacityRequestDTO.getAmountMW())
        .available(capacityRequestDTO.getAmountMW())
        .price(capacityRequestDTO.getPrice())
        .supplier(supplierRepository.findById(user.getId()).get())
        .contractList(new ArrayList<>())
        .build();

    TimeRange timeRange = createTimeRange(capacityRequestDTO.getFrom(), capacityRequestDTO.getTo(), capacity);
    capacity.setTimeRange(timeRange);
    return capacity;
  }

  public TimeRange createTimeRange(String from, String to, Capacity capacity) {
    return TimeRange.builder()
        .from(timeService.localDateTimeTolong(LocalDateTime.parse(from)))
        .to(timeService.localDateTimeTolong(LocalDateTime.parse(to)))
        .capacity(capacity)
        .build();

  }

  public CapacityResponseDTO convert(Capacity capacity) {
    return new CapacityResponseDTO(capacity.getId(), capacity.getEnergySource().toString(), capacity.getAmount(),
        capacity.getAvailable(), capacity.getPrice(),
        timeService.longToLocalDateTime(capacity.getTimeRange().getFrom()),
        timeService.longToLocalDateTime(capacity.getTimeRange().getTo()));
  }

  public Supplier validateUsertype(User user) throws ForbiddenActionException {
    Supplier supplier;
    try {
      return supplier = (Supplier) user;
    }catch (RuntimeException e){
      throw new ForbiddenActionException();
    }
  }

  public void validateGivenDates(String fromString, String toString) throws IllegalArgumentException {
    LocalDateTime from;
    LocalDateTime to;
    LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
    try {
      from = LocalDateTime.parse(fromString);
    } catch (DateTimeException e) {
      throw new IllegalArgumentException("Invalid 'from' date");
    }
    try {
      to = LocalDateTime.parse(toString);
    } catch (DateTimeException e) {
      throw new IllegalArgumentException("Invalid 'to' date");
    }
    if (!from.isAfter(tomorrow) || !to.isAfter(tomorrow)) {
      throw new IllegalArgumentException("Dates accepted from 24h ahead.");
    }
    if (from.isAfter(to)) {
      throw new IllegalArgumentException("'From' date have to be earlier than 'to' date.");
    }
  }

  public void checkCorrectEnergySource(String energySource) throws InvalidEnergySourceException {
    try {
      EnergySource.valueOf(energySource);
    } catch (IllegalArgumentException e) {
      throw new InvalidEnergySourceException();
    }
  }

}
