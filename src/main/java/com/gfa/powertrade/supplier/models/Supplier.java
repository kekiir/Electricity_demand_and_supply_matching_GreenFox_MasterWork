package com.gfa.powertrade.supplier.models;

import com.gfa.powertrade.capacity.models.Capacity;
import com.gfa.powertrade.registration.models.UserType;
import com.gfa.powertrade.user.models.User;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "suppliers")
public class Supplier extends User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String username;
  private String password;

  @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<Capacity> capacityList;

  public Supplier(Integer id, String username, String password, UserType userType,
      Integer id1, String username1, String password1, List<Capacity> capacityList) {
    super(id, username, password, userType);
    this.id = id1;
    this.username = username1;
    this.password = password1;
    this.capacityList = capacityList == null? new ArrayList<>():capacityList;
  }

}
