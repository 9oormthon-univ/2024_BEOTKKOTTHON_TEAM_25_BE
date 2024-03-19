package com.goormthonuniv.ownearth.domain.member;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.goormthonuniv.ownearth.domain.common.BaseEntity;
import com.goormthonuniv.ownearth.domain.enums.SocialType;
import com.goormthonuniv.ownearth.domain.mapping.Friend;
import com.goormthonuniv.ownearth.domain.mapping.MemberItem;
import com.goormthonuniv.ownearth.domain.mapping.MemberMission;
import com.goormthonuniv.ownearth.exception.GlobalErrorCode;
import com.goormthonuniv.ownearth.exception.MemberException;

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

  @Column(nullable = false, length = 10)
  private String name;

  @Column(nullable = false, unique = true, length = 100)
  private String email;

  @Column(nullable = false)
  @Embedded
  private Password password;

  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "VARCHAR(10)")
  private SocialType socialType;

  @Column(nullable = false, length = 30)
  private String earthName;

  @Builder.Default private Integer point = 0;

  @Builder.Default private Integer monthlyPoint = 0;

  @Builder.Default private Boolean isMissionChangeable = true;

  @Column(length = 200)
  private String refreshToken;

  @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<MemberItem> memberItems = new ArrayList<>();

  @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<MemberMission> memberMissions = new ArrayList<>();

  @OneToMany(mappedBy = "aMember", cascade = CascadeType.ALL)
  private List<Friend> aFriends = new ArrayList<>();

  @OneToMany(mappedBy = "bMember", cascade = CascadeType.ALL)
  private List<Friend> bFriends = new ArrayList<>();

  public void updateRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public void setIsMissionChangeable(Boolean missionChangeCheck) {
    this.isMissionChangeable = missionChangeCheck;
  }

  public void decreasePoint() {
    if (this.point < 10) {
      throw new MemberException(GlobalErrorCode.NOT_ENOUGH_POINTS);
    }
    this.point -= 10;
  }
}
