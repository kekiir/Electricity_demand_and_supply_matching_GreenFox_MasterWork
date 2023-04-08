package com.gfa.powerTrade.demand.models;

import com.gfa.powerTrade.common.models.Hour;
import com.gfa.powerTrade.consumers.models.Consumer;
import lombok.*;
import javax.persistence.*;

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
  private Hour hour;
  private float price;
  @ManyToOne
  private Consumer consumer;
}
