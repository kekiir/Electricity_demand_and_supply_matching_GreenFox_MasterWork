package com.gfa.powerTrade.contract.models;

import com.gfa.powerTrade.capacity.models.Capacity;
import com.gfa.powerTrade.demand.models.Demand;
import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "contracts")
public class Contract {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "contract_id")
  private Integer id;
  @ManyToOne
  @JoinColumn(name = "capacity_id")
  private Capacity capacity;
  @ManyToOne
  @JoinColumn(name = "demand_id")
  private Demand demand;


}
