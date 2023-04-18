package com.gfa.powertrade.powerquantity.models;


import com.gfa.powertrade.ballancedhour.models.BalancedHour;
import com.gfa.powertrade.capacity.models.Capacity;
import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "power_quantities")
public class PowerQuantity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "power_quantity_id")
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "capacity_id")
  private Capacity capacity;

  @Column
  private Double powerQuantityAmount;

  @Column(name = "from_time")
  private Long powerQuantityFromTime;

  @Column(name = "to_time")
  private Long powerQuantityToTime;

  @ManyToOne
  @JoinColumn(name = "balanced_hour_id")
  BalancedHour balancedHour;

}



