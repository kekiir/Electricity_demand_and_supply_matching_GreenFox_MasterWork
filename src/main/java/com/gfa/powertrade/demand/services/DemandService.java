package com.gfa.powertrade.demand.services;

import com.gfa.powertrade.common.exceptions.ForbiddenActionException;
import com.gfa.powertrade.common.exceptions.IdNotFoundException;
import com.gfa.powertrade.demand.models.*;
import com.gfa.powertrade.user.models.User;

public interface DemandService {
  public DemandResponseDTO createDemand(User user, DemandRequestDTO demandRequestDto);

  Object updateDemand(DemandUpdateRequestDTO demandUpdateRequestDTO, User user);

  public void deleteDemendById(Integer id, User user) throws IdNotFoundException, IllegalArgumentException,
      ForbiddenActionException;

  public DemandListResponseDTO getDemandsByConsumer(User user);

}
