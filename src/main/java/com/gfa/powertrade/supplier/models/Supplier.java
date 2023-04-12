package com.gfa.powertrade.supplier.models;

import com.gfa.powertrade.capacity.models.Capacity;
import com.gfa.powertrade.user.models.User;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "suppliers")
public class Supplier extends User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String username;
  private String password;

  @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
  private List<Capacity> capacityList;

}
