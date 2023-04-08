package com.gfa.powerTrade.suplier.models;

import com.gfa.powerTrade.capacity.models.Capacity;
import lombok.*;
import javax.persistence.*;
import java.util.List;

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

  @OneToMany(mappedBy = "suplier", cascade = CascadeType.ALL)
  private List<Capacity> capacityList;

}
