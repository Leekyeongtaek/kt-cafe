package com.mrlee.ktcafe.home.member.domain;

import com.mrlee.ktcafe.home.order.domain.Review;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String userName;

    @Builder
    public Member(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }
}
