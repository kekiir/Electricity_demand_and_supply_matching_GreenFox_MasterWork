package com.gfa.powerTrade.consumers.models;

import com.gfa.powerTrade.capacity.models.Capacity;
import com.gfa.powerTrade.demand.models.Demand;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "consumers")
public class Consumer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String username;
  private String password;
  @OneToMany(mappedBy = "consumer", cascade = CascadeType.ALL)
  private List<Demand> demandList;
}
