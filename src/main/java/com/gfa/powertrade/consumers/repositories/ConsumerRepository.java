package com.gfa.powertrade.consumers.repositories;

import com.gfa.powertrade.consumers.models.Consumer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ConsumerRepository extends CrudRepository<Consumer, Integer> {

  Optional<Consumer> findByUsername(String username);

}
