package com.goormthonuniv.ownearth.domain.mapping;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.goormthonuniv.ownearth.domain.Item;
import com.goormthonuniv.ownearth.domain.common.BaseEntity;
import com.goormthonuniv.ownearth.domain.member.Member;

import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MemberItem extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Boolean isUsing;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  @JsonBackReference
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id")
  @JsonBackReference
  private Item item;
}
