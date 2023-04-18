package com.gfa.powertrade.powerquantity.repository;

import com.gfa.powertrade.powerquantity.models.PowerQuantity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PowerQuantityRepository extends CrudRepository<PowerQuantity,Integer> {
}
