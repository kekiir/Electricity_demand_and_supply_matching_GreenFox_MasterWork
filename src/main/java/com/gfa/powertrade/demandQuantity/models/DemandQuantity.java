package com.gfa.powertrade.demandQuantity.models;

import com.gfa.powertrade.ballancedhour.models.BalancedHour;
import com.gfa.powertrade.demand.models.Demand;
import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "demand_quantities")
public class DemandQuantity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "demand_quantity_id")
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "demand_id")
  private Demand demand;
  @Column(name = "amount")
  private Double demandQuantityAmount;
  @Column(name = "from_time")
  private Long from;
  @Column(name = "to_time")
  private Long to;

  @ManyToOne
  @JoinColumn(name = "balanced_hour_id")
  private BalancedHour balancedHour;
}
