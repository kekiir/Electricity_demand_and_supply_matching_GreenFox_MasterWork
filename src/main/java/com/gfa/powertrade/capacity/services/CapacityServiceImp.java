package com.gfa.powertrade.capacity.services;

import com.gfa.powertrade.capacity.models.*;
import com.gfa.powertrade.capacity.repositories.CapacityRepository;
import com.gfa.powertrade.common.exceptions.ForbiddenActionException;
import com.gfa.powertrade.common.exceptions.InvalidEnergySourceException;
import com.gfa.powertrade.common.models.TimeRange;
import com.gfa.powertrade.common.services.TimeService;
import com.gfa.powertrade.registration.models.UserType;
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


  @Override
  public CapacityResponseDTO createCapacity(User user, CapacityRequestDTO capacityRequestDTO) throws
      ForbiddenActionException, IllegalArgumentException, InvalidEnergySourceException {
    validateUsertype(user);
    validateGivenDates(capacityRequestDTO);
    checkCorrectEnergySource(capacityRequestDTO);
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

    TimeRange timeRange = TimeRange.builder()
        .from(timeService.LocalDateTimeTolong(LocalDateTime.parse(capacityRequestDTO.getFrom())))
        .to(timeService.LocalDateTimeTolong(LocalDateTime.parse(capacityRequestDTO.getTo())))
        .capacity(capacity)
        .build();

    capacity.setTimeRange(timeRange);
    return capacity;
  }

  public CapacityResponseDTO convert(Capacity capacity) {
    return new CapacityResponseDTO(capacity.getId(), capacity.getEnergySource().toString(), capacity.getAmount(),
        capacity.getAvailable(), capacity.getPrice(),
        timeService.LongToLocalDateTime(capacity.getTimeRange().getFrom()),
        timeService.LongToLocalDateTime(capacity.getTimeRange().getTo()));
  }

  public void validateUsertype(User user) throws ForbiddenActionException {
    if (!user.getUserType().equals(UserType.SUPPLIER))
      throw new ForbiddenActionException();
  }

  public void validateGivenDates(CapacityRequestDTO capacityRequestDTO) throws IllegalArgumentException {
    LocalDateTime from;
    LocalDateTime to;
LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
    try {
       from = LocalDateTime.parse(capacityRequestDTO.getFrom());
    } catch (DateTimeException e) {
      throw new IllegalArgumentException("Invalid 'from' date");
    }
    try {
     to = LocalDateTime.parse(capacityRequestDTO.getTo());
    } catch (DateTimeException e) {
      throw new IllegalArgumentException("Invalid 'to' date");
    }

    if (!from.isAfter(tomorrow) || !to.isAfter(tomorrow)) {
      throw new IllegalArgumentException("Dates accepted from 24h ahead.");
    }
    if (from.isAfter(to)){
      throw new IllegalArgumentException("'From' date have to be earlier than 'to' date.");
    }


  }

  public void checkCorrectEnergySource(CapacityRequestDTO capacityRequestDTO) throws InvalidEnergySourceException {
    try {
      String energySource = capacityRequestDTO.getEnergySource().toUpperCase();
      EnergySource.valueOf(energySource);
    } catch (IllegalArgumentException e) {
      throw new InvalidEnergySourceException();
    }
  }

}
