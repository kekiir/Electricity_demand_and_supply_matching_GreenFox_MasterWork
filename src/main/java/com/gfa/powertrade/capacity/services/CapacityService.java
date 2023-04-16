package com.gfa.powertrade.capacity.services;

import com.gfa.powertrade.capacity.models.*;
import com.gfa.powertrade.demand.models.DemandListResponseDTO;
import com.gfa.powertrade.user.models.User;

public interface CapacityService {

  CapacityResponseDTO createCapacity(User user, CapacityRequestDTO capacityRequestDTO);

  void deleteCapacityById(Integer id, User user);

  CapacityResponseDTO updateCapacity(CapacityUpdateRequestDTO capacityUpdateRequestDTO, User user);

  CapacityListResponseDTO getCapacitesBySupplier(User user);

  DemandListResponseDTO findDemandsForCapacity(int id, User user);
}
