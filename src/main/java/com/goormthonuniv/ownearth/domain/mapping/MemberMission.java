package com.goormthonuniv.ownearth.domain.mapping;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.goormthonuniv.ownearth.domain.Mission;
import com.goormthonuniv.ownearth.domain.common.BaseEntity;
import com.goormthonuniv.ownearth.domain.member.Member;

import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MemberMission extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  @Builder.Default
  private Boolean isCompleted = false;

  @Column(length = 300)
  private String imageUrl;

  private LocalDateTime completedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mission_id")
  private Mission mission;

  public void missionComplete(String imageUrl) {
    this.imageUrl = imageUrl;
    this.isCompleted = true;
    this.completedAt = LocalDateTime.now();
  }
}
