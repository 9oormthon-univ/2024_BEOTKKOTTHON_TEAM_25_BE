package com.goormthonuniv.ownearth.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.goormthonuniv.ownearth.domain.common.BaseEntity;
import com.goormthonuniv.ownearth.domain.enums.SocialType;
import com.goormthonuniv.ownearth.domain.mapping.Friend;
import com.goormthonuniv.ownearth.domain.mapping.MemberItem;
import com.goormthonuniv.ownearth.domain.mapping.MemberMission;

import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Member extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, columnDefinition = "VARCHAR(10)")
  private SocialType socialType;

  private String earthName;

  @Builder.Default private Integer point = 0;

  @Builder.Default private Integer monthlyPoint = 0;

  @OneToMany(mappedBy = "member_id", cascade = CascadeType.ALL)
  private List<MemberItem> memberItems = new ArrayList<>();

  @OneToMany(mappedBy = "member_id", cascade = CascadeType.ALL)
  private List<MemberMission> memberMissions = new ArrayList<>();

  @OneToMany(mappedBy = "aMember", cascade = CascadeType.ALL)
  private List<Friend> aFriends = new ArrayList<>();

  @OneToMany(mappedBy = "bMember", cascade = CascadeType.ALL)
  private List<Friend> bFriends = new ArrayList<>();
}
