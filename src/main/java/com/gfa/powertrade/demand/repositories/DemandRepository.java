package com.gfa.powertrade.demand.repositories;

import com.gfa.powertrade.demand.models.Demand;
import org.springframework.data.repository.CrudRepository;

public interface DemandRepository extends CrudRepository<Demand, Integer> {
}
