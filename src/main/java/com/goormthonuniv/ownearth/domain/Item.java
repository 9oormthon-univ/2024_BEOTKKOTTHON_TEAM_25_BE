package com.goormthonuniv.ownearth.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.goormthonuniv.ownearth.domain.enums.ItemCategory;
import com.goormthonuniv.ownearth.domain.mapping.MemberItem;

import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Integer price;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, columnDefinition = "VARCHAR(10)")
  private ItemCategory itemCategory;

  @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL)
  private List<MemberItem> memberItems = new ArrayList<>();
}
