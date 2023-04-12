package com.gfa.powertrade.consumers.models;

import com.gfa.powertrade.user.models.User;
import com.gfa.powertrade.demand.models.Demand;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "consumers")
public class Consumer extends User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String username;
  private String password;
  @OneToMany(mappedBy = "consumer", cascade = CascadeType.ALL)
  private List<Demand> demandList;
}
