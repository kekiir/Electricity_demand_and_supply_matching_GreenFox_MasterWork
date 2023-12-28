package com.gfa.powertrade.capacity.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gfa.powertrade.powerquantity.models.PowerQuantity;
import com.gfa.powertrade.common.exceptions.ContractedCapacityException;
import com.gfa.powertrade.contract.models.Contract;
import com.gfa.powertrade.supplier.models.Supplier;
import lombok.*;
import org.hibernate.annotations.*;
import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

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
  @OnDelete(action = OnDeleteAction.CASCADE)
  private List<PowerQuantity> powerQuantityList;




  public void setCapacityAmount(Double newAmount) {
    Double contractedAmount = capacityAmount - available;
    if (this.capacityAmount - this.available > newAmount)
      throw new ContractedCapacityException();
    double oldAmount = capacityAmount;
    this.capacityAmount = newAmount;
    this.setAvailable(available - (oldAmount - newAmount));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Capacity))
      return false;
    Capacity capacity = (Capacity) o;
    return Objects.equals(id, capacity.id) && energySource == capacity.energySource && Objects.equals(
      capacityAmount, capacity.capacityAmount) && Objects.equals(available,
      capacity.available) && Objects.equals(capacityFromTime,
      capacity.capacityFromTime) && Objects.equals(capacityToTime,
      capacity.capacityToTime) && Objects.equals(price, capacity.price) && Objects.equals(supplier,
      capacity.supplier) && Objects.equals(contractList, capacity.contractList) && Objects.equals(
      powerQuantityList, capacity.powerQuantityList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, energySource, capacityAmount, available, capacityFromTime, capacityToTime, price, supplier,
      contractList, powerQuantityList);
  }

}
