package com.gfa.powertrade.demand.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gfa.powertrade.common.models.TimeRange;
import com.gfa.powertrade.consumers.models.Consumer;
import com.gfa.powertrade.contract.models.Contract;
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
@Table(name = "demands")
public class Demand {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "demand_id")
  private Integer id;
  private Double demandAmount;
  private Double remained;
  private Long demandFromTime;
  private Long demandToTime;



  private Double price;
  @JsonIgnore
  @ManyToOne
  private Consumer consumer;
  @OneToMany(mappedBy = "demand", cascade = CascadeType.ALL)
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<Contract> contractList;

}
