package com.gfa.powertrade.demandquantity.repositories;

import com.gfa.powertrade.demandquantity.models.DemandQuantity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface DemandQuantityRepository extends CrudRepository<DemandQuantity, Integer> {

  @Modifying
  @Transactional
  @Query("DELETE FROM DemandQuantity p WHERE p IN (:demandQuantityList)")
  void deleteInBatch(List<DemandQuantity> demandQuantityList);

}
