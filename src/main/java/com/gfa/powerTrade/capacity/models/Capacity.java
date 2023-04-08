package com.gfa.powerTrade.capacity.models;

import com.gfa.powerTrade.suplier.models.Suplier;
import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "capacities")
public class Capacity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "capacity_id")
  private Integer id;
  private float amount;
  private float available;
  @OneToOne(mappedBy = "capacity",cascade = CascadeType.ALL)
  private Hour hour;
  private float price;
  @ManyToOne
  private Suplier suplier;

}
