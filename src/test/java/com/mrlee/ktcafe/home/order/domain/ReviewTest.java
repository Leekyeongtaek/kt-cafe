package com.mrlee.ktcafe.home.order.domain;

import com.mrlee.ktcafe.home.order.OrderFixture;
import com.mrlee.ktcafe.home.order.service.dto.UpdateReviewInput;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.mrlee.ktcafe.home.order.OrderFixture.*;
import static org.junit.jupiter.api.Assertions.*;

class ReviewTest {

    @Test
    void UpdateReviewInput_객체로_Review_객체를_수정할_수_있다() {
        Review review = anReview().build();
        UpdateReviewInput updateReviewInput = anUpdateReviewInput().build();

        review.update(updateReviewInput);

        Assertions.assertThat(review.getRating()).isEqualTo(4);
        Assertions.assertThat(review.getContents()).isEqualTo("맛있어요!! 변경");
        Assertions.assertThat(review.getPhotoImage()).isEqualTo("www.photo.com 변경");
    }
}