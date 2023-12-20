package com.mrlee.ktcafe.home.order.service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
public class UpdateReviewInput {

    @NotNull
    private Long memberId;
    @Range(min = 1, max = 5)
    private int rating;
    @NotEmpty
    private String contents;
    private String photoImage;

    @Builder
    public UpdateReviewInput(@NotNull Long memberId, int rating, String contents, String photoImage) {
        this.memberId = memberId;
        this.rating = rating;
        this.contents = contents;
        this.photoImage = photoImage;
    }
}
