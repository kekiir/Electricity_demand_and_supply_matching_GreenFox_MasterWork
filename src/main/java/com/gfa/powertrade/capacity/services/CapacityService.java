package com.gfa.powertrade.capacity.services;

import com.gfa.powertrade.capacity.models.CapacityRequestDTO;
import com.gfa.powertrade.capacity.models.CapacityResponseDTO;
import com.gfa.powertrade.user.models.User;

public interface CapacityService {
  CapacityResponseDTO createCapacity(User user, CapacityRequestDTO capacityRequestDTO);

}
