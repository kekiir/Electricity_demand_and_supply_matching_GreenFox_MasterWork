package com.gfa.powertrade.supplier.repository;

import com.gfa.powertrade.supplier.models.Supplier;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface SupplierRepository extends CrudRepository<Supplier, Integer> {

  Optional<Supplier> findByUsername(String username);

  List<Supplier> findAll();

}
