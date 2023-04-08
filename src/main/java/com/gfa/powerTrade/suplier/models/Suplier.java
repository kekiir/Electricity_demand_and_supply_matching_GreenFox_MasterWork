package com.gfa.powerTrade.suplier.models;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "supliers")
public class Suplier {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String username;
  private String password;
  @Column(columnDefinition = "enum('COAL','GAS','NUCLEAR','HYDRO','WIND','SOLAR','BIO','WASTE')")
  private EnergySource energySource;

}
