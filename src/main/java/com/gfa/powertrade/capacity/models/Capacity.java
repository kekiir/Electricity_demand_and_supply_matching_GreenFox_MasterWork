package com.gfa.powertrade.capacity.models;

import com.gfa.powertrade.common.models.TimeRange;
import com.gfa.powertrade.contract.Contract;
import com.gfa.powertrade.supplier.models.Supplier;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "capacities")
public class Capacity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "capacity_id")
  private Integer id;

  @Enumerated(EnumType.STRING)
  @Column(name = "energy_source", columnDefinition = "ENUM('COAL','GAS','NUCLEAR','HYDRO','WIND','SOLAR','BIO','WASTE')")
  private EnergySource energySource;
  private Double amount;
  private Double available;
  @OneToOne(mappedBy = "capacity",cascade = CascadeType.ALL)
  private TimeRange timeRange;
  private Double price;
  @ManyToOne
  private Supplier supplier;
  @OneToMany (mappedBy = "capacity")
  private List<Contract> contractList;

}
