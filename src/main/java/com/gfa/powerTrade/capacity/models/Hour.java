package com.gfa.powerTrade.capacity.models;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hours")
public class Hour {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "hour_id")
  private Integer id;
  @Column(name = "hour_from")
  private Date from;
  @Column(name = "hour_to")
  private Date to;
  @Column(name = "hour_day")
  private Date day;
  @OneToOne
  @JoinColumn(name = "capacity_id")
  private Capacity capacity;

}
