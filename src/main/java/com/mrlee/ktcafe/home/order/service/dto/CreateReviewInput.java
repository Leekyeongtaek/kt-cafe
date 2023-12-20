package com.mrlee.ktcafe.home.order.service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
public class CreateReviewInput {

    @NotNull
    private Long OrderId;
    @NotNull
    private Long memberId;
    @Range(min = 1, max = 5)
    private int rating;
    @NotEmpty
    private String contents;
    private String photoImage;

    @Builder
    public CreateReviewInput(@NotNull Long orderId, @NotNull Long memberId, int rating, String contents, String photoImage) {
        OrderId = orderId;
        this.memberId = memberId;
        this.rating = rating;
        this.contents = contents;
        this.photoImage = photoImage;
    }
}
