package com.gfa.powertrade.demand.services;

import com.gfa.powertrade.capacity.models.Capacity;
import com.gfa.powertrade.capacity.models.CapacityListResponseDTO;
import com.gfa.powertrade.capacity.repositories.CapacityRepository;
import com.gfa.powertrade.common.exceptions.*;
import com.gfa.powertrade.common.services.ConverterService;
import com.gfa.powertrade.common.services.TimeServiceImp;
import com.gfa.powertrade.consumers.models.Consumer;
import com.gfa.powertrade.consumers.repositories.ConsumerRepository;
import com.gfa.powertrade.demand.models.*;
import com.gfa.powertrade.demand.repositories.DemandRepository;
import com.gfa.powertrade.demandquantity.repositories.DemandQuantityRepository;
import com.gfa.powertrade.demandquantity.services.DemandQuantityService;
import com.gfa.powertrade.user.models.User;
import com.gfa.powertrade.user.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DemandServiceImp implements DemandService {

  private ConsumerRepository consumerRepository;
  private TimeServiceImp timeService;
  private DemandRepository demandRepository;
  private CapacityRepository capacityRepository;
  private ConverterService converterService;
  private UserService userService;
  private DemandQuantityService demandQuantityService;
  private DemandQuantityRepository demandQuantityRepository;

  @Override
  public Object findCapacitiesForDemand(Integer id, User user) {
    userService.validateConsumertype(user);
    Long demandFromTime = demandRepository.findById(id).get().getDemandFromTime();
    Long demandToTime = demandRepository.findById(id).get().getDemandToTime();

    List<Capacity> capList = capacityRepository.findAll();
    return new CapacityListResponseDTO(capacityRepository.findAll().stream()
        .filter(c -> c.getCapacityToTime() > demandFromTime)
        .filter(c -> c.getCapacityFromTime() < demandToTime)
        .map(c -> converterService.convertCapacityToResponseDTO(c))
        .collect(Collectors.toList()));
  }

  @Override
  public void deleteDemendById(Integer id, User user) throws IdNotFoundException, IllegalArgumentException,
      ForbiddenActionException {
    Consumer consumer = userService.validateConsumertype(user);

    Demand demand = demandRepository.findById(id)
        .orElseThrow(() -> new IdNotFoundException());
    demandBelongsToConsumer(demand, consumer.getId());

    demandQuantityRepository.deleteInBatch(demand.getDemandQuantityList());
    demand.getDemandQuantityList().removeAll(demand.getDemandQuantityList());
    consumer.getDemandList().removeIf(d -> d.getId().equals(id));
    consumerRepository.save(consumer);
    demandRepository.deleteById(id);

  }

  @Override
  public Object updateDemand(DemandUpdateRequestDTO demandUpdateRequestDTO, User user) {
    Consumer consumer = userService.validateConsumertype(user);
    Demand demand = demandRepository.findById(demandUpdateRequestDTO.getId())
        .orElseThrow(() -> new IdNotFoundException());
    demandBelongsToConsumer(demand, consumer.getId());
    timeService.validateGivenDates(demandUpdateRequestDTO.getFromTime(), demandUpdateRequestDTO.getToTime());

   demandQuantityService.updateDemandQuantites(demandUpdateRequestDTO, demand);

    updataDemand(demandUpdateRequestDTO, demand);
    return converterService.convertDemandToResponseDTO(demandRepository.save(demand));
  }

  private void updataDemand(DemandUpdateRequestDTO demandUpdateRequestDTO, Demand demand) {
    String from = demandUpdateRequestDTO.getFromTime();
    String to = demandUpdateRequestDTO.getToTime();
    demand.setId(demandUpdateRequestDTO.getId());
    demand.setDemandAmount(demandUpdateRequestDTO.getAmountMW());
    demand.setPrice(demandUpdateRequestDTO.getPrice());
    demand.setDemandFromTime(timeService.localDateTimeTolong(LocalDateTime.parse(from)));
    demand.setDemandToTime(timeService.localDateTimeTolong(LocalDateTime.parse(to)));

  }

  private void demandBelongsToConsumer(Demand demand, Integer userId) throws ForbiddenActionException {
    if (userId != demand.getConsumer().getId())
      throw new ForbiddenActionException();
  }

  @Override
  public DemandResponseDTO createDemand(User user, DemandRequestDTO demandRequestDTO) throws
      ForbiddenActionException, IllegalArgumentException, InvalidEnergySourceException {
    userService.validateConsumertype(user);
    timeService.validateGivenDates(demandRequestDTO.getFromTime(), demandRequestDTO.getToTime());
    Demand demand = setDemandVariables(demandRequestDTO, user);
    demand = demandRepository.save(demand);
    demandQuantityService.createDemandQuantities(demand, demand.getDemandFromTime(), demand.getDemandToTime());
    return converterService.convertDemandToResponseDTO(demand);
  }

  private Demand setDemandVariables(DemandRequestDTO demandRequestDTO, User user) {
    Demand demand = Demand.builder()
        .demandAmount(demandRequestDTO.getAmountMW())
        .remained(0d)
        .price(demandRequestDTO.getPrice())
        .demandFromTime(timeService.localDateTimeTolong(LocalDateTime.parse(demandRequestDTO.getFromTime())))
        .demandToTime(timeService.localDateTimeTolong(LocalDateTime.parse(demandRequestDTO.getToTime())))
        .consumer(consumerRepository.findById(user.getId()).get())
        .contractList(new ArrayList<>())
        .demandQuantityList(new ArrayList<>())
        .build();

    return demand;
  }

  @Override
  public DemandListResponseDTO getDemandsByConsumer(User user) {
    Consumer consumer = userService.validateConsumertype(user);

    return converterService.convertDemandToDemandListDTO(consumer.getDemandList());
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
