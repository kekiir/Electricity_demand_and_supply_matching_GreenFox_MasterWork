package com.gfa.powertrade.common.models;

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
@Table(name = "time_ranges")
public class TimeRange {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;
  @Column(name = "from_time")
  private Long from;
  @Column(name = "to_time")
  private Long to;
  @OneToOne
  @JoinColumn(name = "capacity_id")
  private Capacity capacity;
  @OneToOne
  @JoinColumn(name = "demand_id")
  private Demand demand;

}
