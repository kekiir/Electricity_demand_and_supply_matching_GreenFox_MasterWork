package com.gfa.powertrade.demand.repositories;

import com.gfa.powertrade.demand.models.Demand;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DemandRepository extends CrudRepository<Demand, Integer> {


  List<Demand> findAll();
}
