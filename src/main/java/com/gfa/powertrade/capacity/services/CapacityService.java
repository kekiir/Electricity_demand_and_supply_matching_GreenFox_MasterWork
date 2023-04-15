package com.gfa.powertrade.capacity.services;

import com.gfa.powertrade.capacity.models.*;
import com.gfa.powertrade.user.models.User;

public interface CapacityService {

  CapacityResponseDTO createCapacity(User user, CapacityRequestDTO capacityRequestDTO);

  void deleteCapacityById(Integer id, User user);

  CapacityUpdatedResponseDTO updateCapacityById(CapacityUpdateRequestDTO capacityUpdateRequestDTO, User user);

}
