package com.gfa.powertrade.powerquantity.repository;

import com.gfa.powertrade.powerquantity.models.PowerQuantity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PowerQuantityRepository extends CrudRepository<PowerQuantity,Integer> {

  @Modifying
  @Transactional
  @Query("DELETE FROM PowerQuantity p WHERE p IN (:powerQuantityList)")
  void deleteInBatch(List<PowerQuantity> powerQuantityList);

}
