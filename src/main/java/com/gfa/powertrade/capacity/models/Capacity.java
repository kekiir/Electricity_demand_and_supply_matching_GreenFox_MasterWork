package com.gfa.powertrade.capacity.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gfa.powertrade.powerquantity.models.PowerQuantity;
import com.gfa.powertrade.common.exceptions.ContractedCapacityException;
import com.gfa.powertrade.contract.models.Contract;
import com.gfa.powertrade.supplier.models.Supplier;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
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
  @Column(name = "energy_source",
      columnDefinition = "ENUM('COAL','GAS','NUCLEAR','HYDRO','WIND','SOLAR','BIO','WASTE')")
  private EnergySource energySource;
  private Double capacityAmount;
  private Double available;
  private Long capacityFromTime;
  private Long capacityToTime;


  private Double price;
  @JsonIgnore
  @ManyToOne
  private Supplier supplier;
  @OneToMany(mappedBy = "capacity", cascade = CascadeType.ALL)
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<Contract> contractList;

  @OneToMany(mappedBy = "capacity", cascade = CascadeType.ALL)
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<PowerQuantity> powerQuantityList;




  public void setCapacityAmount(Double newAmount) {
    Double contractedAmount = capacityAmount - available;
    if (this.capacityAmount - this.available > newAmount)
      throw new ContractedCapacityException();
    double oldAmount = capacityAmount;
    this.capacityAmount = newAmount;
    this.setAvailable(available - (oldAmount - newAmount));
  }


}
