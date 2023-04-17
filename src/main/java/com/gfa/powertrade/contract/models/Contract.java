package com.gfa.powertrade.contract.models;

import com.gfa.powertrade.capacity.models.Capacity;
import com.gfa.powertrade.demand.models.Demand;
import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "contracts")
public class Contract {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "contract_id")
  private Integer id;

  private Double contractAmount;

  @ManyToOne
  @JoinColumn(name = "capacity_id")
  private Capacity capacity;

  @ManyToOne
  @JoinColumn(name = "demand_id")
  private Demand demand;

}
