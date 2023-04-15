package com.gfa.powertrade.capacity.models;

import com.gfa.powertrade.common.exceptions.ContractedCapacityException;
import com.gfa.powertrade.common.models.TimeRange;
import com.gfa.powertrade.contract.Contract;
import com.gfa.powertrade.supplier.models.Supplier;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import javax.persistence.*;
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
  @Column(name = "energy_source", columnDefinition = "ENUM('COAL','GAS','NUCLEAR','HYDRO','WIND','SOLAR','BIO','WASTE')")
  private EnergySource energySource;
  private Double amount;
  private Double available;
  @OneToOne(mappedBy = "capacity", cascade = CascadeType.ALL)
  @JoinColumn(name = "timerange_id")
  private TimeRange timeRange;
  private Double price;
  @ManyToOne
  private Supplier supplier;
  @OneToMany(mappedBy = "capacity", cascade = CascadeType.ALL)
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<Contract> contractList;

  public void setAmount(Double newAmount) {
    Double contractedAmount = amount-available;
    if (this.amount - this.available > newAmount)
      throw new ContractedCapacityException();
    double oldAmount = amount;
    this.amount = newAmount;
    this.setAvailable(available - (oldAmount - newAmount));
//    if (available < 0) {
//      throw new ContractedCapacityException();
//    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Capacity capacity = (Capacity) o;
    return Objects.equals(id, capacity.id) && energySource == capacity.energySource && Objects.equals(
        amount, capacity.amount) && Objects.equals(available, capacity.available) && Objects.equals(
        timeRange, capacity.timeRange) && Objects.equals(price, capacity.price) && Objects.equals(
        supplier, capacity.supplier) && Objects.equals(contractList, capacity.contractList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, energySource, amount, available, timeRange, price, supplier, contractList);
  }

}
