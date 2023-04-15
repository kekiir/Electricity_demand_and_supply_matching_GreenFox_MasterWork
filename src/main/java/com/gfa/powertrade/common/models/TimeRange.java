package com.gfa.powertrade.common.models;

import com.gfa.powertrade.capacity.models.Capacity;
import com.gfa.powertrade.demand.models.Demand;
import lombok.*;
import javax.persistence.*;
import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TimeRange timeRange = (TimeRange) o;
    return id.equals(timeRange.id) && from.equals(timeRange.from) && to.equals(timeRange.to) && Objects.equals(
        capacity, timeRange.capacity) && Objects.equals(demand, timeRange.demand);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, from, to, capacity, demand);
  }

}
