package com.gfa.powertrade.capacity.repositories;

import com.gfa.powertrade.capacity.models.Capacity;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CapacityRepository extends CrudRepository<Capacity, Integer> {
  List<Capacity> findAll();

}
