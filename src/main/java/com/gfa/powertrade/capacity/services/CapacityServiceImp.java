package com.gfa.powertrade.capacity.services;

import com.gfa.powertrade.capacity.models.*;
import com.gfa.powertrade.capacity.repositories.CapacityRepository;
import com.gfa.powertrade.common.exceptions.*;
import com.gfa.powertrade.common.models.TimeRange;
import com.gfa.powertrade.common.models.TimeRangeRepository;
import com.gfa.powertrade.common.services.TimeService;
import com.gfa.powertrade.supplier.models.Supplier;
import com.gfa.powertrade.supplier.repository.SupplierRepository;
import com.gfa.powertrade.user.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CapacityServiceImp implements CapacityService {

  private SupplierRepository supplierRepository;
  private TimeService timeService;
  private CapacityRepository capacityRepository;
  private TimeRangeRepository timeRangeRepository;

  @Override
  public void deleteCapacityById(Integer id, User user) throws IdNotFoundException, IllegalArgumentException,
      ForbiddenActionException {
    Supplier supplier = validateUsertype(user);

    Capacity capacity = capacityRepository.findById(id)
        .orElseThrow(() -> new IdNotFoundException());
    capacityBelongsToSupplier(capacity, supplier.getId());
    capacityRepository.delete(capacity);

  }

  @Override
  public CapacityResponseDTO updateCapacity(CapacityUpdateRequestDTO capacityUpdateRequestDTO, User user) {
    Supplier supplier = validateUsertype(user);
    checkCorrectEnergySource(capacityUpdateRequestDTO.getEnergySource());
    Capacity capacity = capacityRepository.findById(capacityUpdateRequestDTO.getId())
        .orElseThrow(() -> new IdNotFoundException());
    capacityBelongsToSupplier(capacity, supplier.getId());
    timeService.validateGivenDates(capacityUpdateRequestDTO.getFrom(), capacityUpdateRequestDTO.getTo());
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

  private CapacityResponseDTO convertTOUpdatedResponseDTO(Capacity capacity) {
    return new CapacityResponseDTO(capacity.getId(), capacity.getEnergySource().toString(),
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
    timeService.validateGivenDates(capacityRequestDTO.getFrom(), capacityRequestDTO.getTo());
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

  @Override
  public CapacityListResponseDTO getCapacitesBySupplier(User user) {
    Supplier supplier = validateUsertype(user);

    return convertToCapacityDTOList(supplier.getCapacityList());
  }

  private CapacityListResponseDTO convertToCapacityDTOList(List<Capacity> capacityList) {
    List<CapacityResponseDTO> list = capacityList.stream()
        .map(this::convert)
        .collect(Collectors.toList());

    return new CapacityListResponseDTO(list);
  }

  public Supplier validateUsertype(User user) throws ForbiddenActionException {
    Supplier supplier;
    try {
      return supplier = (Supplier) user;
    } catch (RuntimeException e) {
      throw new ForbiddenActionException();
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
