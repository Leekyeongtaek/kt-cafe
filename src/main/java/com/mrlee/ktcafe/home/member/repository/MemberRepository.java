package com.mrlee.ktcafe.home.member.repository;

import com.mrlee.ktcafe.home.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
