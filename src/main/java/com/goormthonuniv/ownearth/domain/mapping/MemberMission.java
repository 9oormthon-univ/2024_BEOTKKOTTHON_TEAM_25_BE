package com.goormthonuniv.ownearth.domain.mapping;

import com.goormthonuniv.ownearth.domain.Member;
import com.goormthonuniv.ownearth.domain.Mission;
import com.goormthonuniv.ownearth.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.joda.time.DateTime;

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
    private Boolean isCompleted;

    @Column(nullable = false)
    private String imageLink;

    private DateTime completedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

}
