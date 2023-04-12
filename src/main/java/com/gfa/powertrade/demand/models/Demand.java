package com.gfa.powertrade.demand.models;

import com.gfa.powertrade.common.models.TimeRange;
import com.gfa.powertrade.consumers.models.Consumer;
import com.gfa.powertrade.contract.models.Contract;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "demands")
public class Demand {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "demand_id")
  private Integer id;
  private float amount;
  private float covered;
  @OneToOne(mappedBy = "demand",cascade = CascadeType.ALL)
  private TimeRange timeRange;
  private float price;
  @ManyToOne
  private Consumer consumer;
  @OneToMany (mappedBy = "demand")
  private List<Contract> contractList;
}
