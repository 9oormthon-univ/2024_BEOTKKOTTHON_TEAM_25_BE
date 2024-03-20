package com.goormthonuniv.ownearth.domain.mapping;

import jakarta.persistence.*;

import com.goormthonuniv.ownearth.domain.common.BaseEntity;
import com.goormthonuniv.ownearth.domain.member.Member;

import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Friend extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Boolean isFriend;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "from_member_id")
  private Member fromMember;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "to_member_id")
  private Member toMember;
}
