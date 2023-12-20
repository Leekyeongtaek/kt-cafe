package com.mrlee.ktcafe.home.order.domain;

import com.mrlee.ktcafe.common.BaseTime;
import com.mrlee.ktcafe.home.member.domain.Member;
import com.mrlee.ktcafe.home.order.service.dto.UpdateReviewInput;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Review extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;
    private int rating;
    private String contents;
    private String photoImage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static Review createReview(Order order, Member member, int rating, String contents, String photoImage) {
        return Review.builder()
                .order(order)
                .member(member)
                .rating(rating)
                .contents(contents)
                .photoImage(photoImage)
                .build();
    }

    @Builder
    public Review(Long id, int rating, String contents, String photoImage, Order order, Member member) {
        this.id = id;
        this.member = member;
        this.rating = rating;
        this.contents = contents;
        this.photoImage = photoImage;
        this.order = order;
    }

    public void update(UpdateReviewInput updateReviewInput) {
        this.rating = updateReviewInput.getRating();
        this.contents = updateReviewInput.getContents();
        this.photoImage = updateReviewInput.getPhotoImage();
    }

    public boolean isReviewMember(Long memberId) {
        return Objects.equals(member.getId(), memberId);
    }
}
