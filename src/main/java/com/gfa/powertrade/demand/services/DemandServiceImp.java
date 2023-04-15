package com.gfa.powertrade.demand.services;

import com.gfa.powertrade.common.exceptions.*;
import com.gfa.powertrade.common.models.TimeRange;
import com.gfa.powertrade.common.models.TimeRangeRepository;
import com.gfa.powertrade.common.services.TimeService;
import com.gfa.powertrade.consumers.models.Consumer;
import com.gfa.powertrade.consumers.repositories.ConsumerRepository;
import com.gfa.powertrade.demand.models.*;
import com.gfa.powertrade.demand.repositories.DemandRepository;
import com.gfa.powertrade.user.models.User;
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
  private TimeService timeService;
  private DemandRepository demandRepository;
  private TimeRangeRepository timeRangeRepository;

  @Override
  public void deleteDemendById(Integer id, User user) throws IdNotFoundException, IllegalArgumentException,
      ForbiddenActionException {
    Consumer consumer = validateUsertype(user);

    Demand demand = demandRepository.findById(id)
        .orElseThrow(() -> new IdNotFoundException());
    demandBelongsToConsumer(demand, consumer.getId());
    demandRepository.delete(demand);

  }

  @Override
  public Object updateDemand(DemandUpdateRequestDTO demandUpdateRequestDTO, User user) {
    Consumer consumer = validateUsertype(user);
    Demand demand = demandRepository.findById(demandUpdateRequestDTO.getId())
        .orElseThrow(() -> new IdNotFoundException());
    demandBelongsToConsumer(demand, consumer.getId());
    timeService.validateGivenDates(demandUpdateRequestDTO.getFrom(), demandUpdateRequestDTO.getTo());
    updataDemand(demandUpdateRequestDTO, demand);
    return convertTOUpdatedResponseDTO(demandRepository.save(demand));
  }

  private void updataDemand(DemandUpdateRequestDTO demandUpdateRequestDTO, Demand demand) {
    demand.setId(demandUpdateRequestDTO.getId());
    demand.setAmount(demandUpdateRequestDTO.getAmountMW());
    demand.setPrice(demandUpdateRequestDTO.getPrice());
    updateTimeRange(demandUpdateRequestDTO, demand);

  }

  public void updateTimeRange(DemandUpdateRequestDTO demandUpdateRequestDTO, Demand demand) {
    String from = demandUpdateRequestDTO.getFrom();
    String to = demandUpdateRequestDTO.getTo();
    demand.getTimeRange().setFrom(timeService.localDateTimeTolong(LocalDateTime.parse(from)));
    demand.getTimeRange().setTo(timeService.localDateTimeTolong(LocalDateTime.parse(to)));
    timeRangeRepository.save(demand.getTimeRange());
  }

  private DemandResponseDTO convertTOUpdatedResponseDTO(Demand demand) {
    return new DemandResponseDTO(demand.getId(),
        demand.getAmount(), demand.getCovered(), demand.getPrice(),
        timeService.longToLocalDateTime(demand.getTimeRange().getFrom()),
        timeService.longToLocalDateTime(demand.getTimeRange().getTo()));
  }

  private void demandBelongsToConsumer(Demand demand, Integer userId) throws ForbiddenActionException {
    if (userId != demand.getConsumer().getId())
      throw new ForbiddenActionException();
  }

  @Override
  public DemandResponseDTO createDemand(User user, DemandRequestDTO demandRequestDTO) throws
      ForbiddenActionException, IllegalArgumentException, InvalidEnergySourceException {
    validateUsertype(user);
    timeService.validateGivenDates(demandRequestDTO.getFrom(), demandRequestDTO.getTo());
    Demand demand = setDemandVariables(demandRequestDTO, user);

    return convert(demandRepository.save(demand));
  }

  private Demand setDemandVariables(DemandRequestDTO demandRequestDTO, User user) {
    Demand demand = Demand.builder()
        .amount(demandRequestDTO.getAmountMW())
        .covered(0d)
        .price(demandRequestDTO.getPrice())
        .consumer(consumerRepository.findById(user.getId()).get())
        .contractList(new ArrayList<>())
        .build();
    TimeRange timeRange = createTimeRange(demandRequestDTO.getFrom(), demandRequestDTO.getTo(), demand);
    demand.setTimeRange(timeRange);
    return demand;
  }

  public TimeRange createTimeRange(String from, String to, Demand demand) {
    return TimeRange.builder()
        .from(timeService.localDateTimeTolong(LocalDateTime.parse(from)))
        .to(timeService.localDateTimeTolong(LocalDateTime.parse(to)))
        .demand(demand)
        .build();

  }

  public DemandResponseDTO convert(Demand demand) {
    return new DemandResponseDTO(demand.getId(), demand.getAmount(),
        demand.getCovered(), demand.getPrice(),
        timeService.longToLocalDateTime(demand.getTimeRange().getFrom()),
        timeService.longToLocalDateTime(demand.getTimeRange().getTo()));
  }

  @Override
  public DemandListResponseDTO getDemandsByConsumer(User user) {
    Consumer consumer = validateUsertype(user);

    return convertToDemandDTOList(consumer.getDemandList());
  }

  private DemandListResponseDTO convertToDemandDTOList(List<Demand> demandList) {
    List<DemandResponseDTO> list = demandList.stream()
        .map(this::convert)
        .collect(Collectors.toList());

    return new DemandListResponseDTO(list);
  }

  public Consumer validateUsertype(User user) throws ForbiddenActionException {
    Consumer consumer;
    try {
      return consumer = (Consumer) user;
    } catch (RuntimeException e) {
      throw new ForbiddenActionException();
    }
  }

}
