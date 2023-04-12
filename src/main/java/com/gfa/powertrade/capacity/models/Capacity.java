package com.gfa.powertrade.capacity.models;

import com.gfa.powertrade.common.models.TimeRange;
import com.gfa.powertrade.contract.models.Contract;
import com.gfa.powertrade.supplier.models.EnergySource;
import com.gfa.powertrade.supplier.models.Supplier;
import lombok.*;
import javax.persistence.*;
import java.util.List;

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
  @Column(columnDefinition = "enum('COAL','GAS','NUCLEAR','HYDRO','WIND','SOLAR','BIO','WASTE')")
  private EnergySource energySource;
  private float amount;
  private float available;
  @OneToOne(mappedBy = "capacity",cascade = CascadeType.ALL)
  private TimeRange timeRange;
  private float price;
  @ManyToOne
  private Supplier supplier;
  @OneToMany (mappedBy = "capacity")
  private List<Contract> contractList;

}
