package com.goormthonuniv.ownearth.domain.mapping;

import com.goormthonuniv.ownearth.domain.Member;
import com.goormthonuniv.ownearth.domain.common.BaseEntity;
import com.goormthonuniv.ownearth.domain.enums.FriendStatus;
import jakarta.persistence.*;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(10)")
    private FriendStatus friendStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "a_member_id")
    private Member aMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "b_member_id")
    private Member bmember;

}
