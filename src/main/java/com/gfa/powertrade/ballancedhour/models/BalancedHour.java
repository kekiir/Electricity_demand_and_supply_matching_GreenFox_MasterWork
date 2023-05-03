package com.gfa.powertrade.ballancedhour.models;

import com.gfa.powertrade.demandquantity.models.DemandQuantity;
import com.gfa.powertrade.powerquantity.models.PowerQuantity;
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
@Table(name = "balanced_hours")
public class BalancedHour {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "balanced_hour_id")
  private Integer id;
  @Column(name = "balanced_hour_price")
  private Double balancedHourPrice;

  @Column(name = "balanced_hour_from_time")
  private Long balancedHourFromTime;
  @Column(name = "balanced_hour_to_time")
  private Long balancedHourToTime;

  @OneToMany(mappedBy = "balancedHour", cascade = CascadeType.PERSIST)
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<PowerQuantity> powerQuantityList;

  @OneToMany(mappedBy = "balancedHour", cascade = CascadeType.PERSIST)
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<DemandQuantity> demandQuantityList;

}
