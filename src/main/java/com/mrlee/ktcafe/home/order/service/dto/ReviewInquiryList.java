package com.mrlee.ktcafe.home.order.service.dto;

import com.mrlee.ktcafe.home.order.domain.OrderProduct;
import com.mrlee.ktcafe.home.order.domain.Review;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ReviewInquiryList {

    private Long reviewId;
    private String userName;
    private int rating;
    private String contents;
    private String photoImage;
    private LocalDateTime registrationDate;
    private List<ReviewProduct> reviewProductList = new ArrayList<>();

    public ReviewInquiryList(Review review) {
        this.reviewId = review.getId();
        this.userName = review.getMember().getUserName();
        this.rating = review.getRating();
        this.contents = review.getContents();
        this.photoImage = review.getPhotoImage();
        this.registrationDate = review.getCreatedDate();
        this.reviewProductList = review.getOrder().getOrderProducts().stream().map(ReviewProduct::new).toList();
    }

    @Data
    @NoArgsConstructor
    public static class ReviewProduct {
        private Long productId;
        private String name;

        public ReviewProduct(OrderProduct orderProduct) {
            this.productId = orderProduct.getProductId();
            this.name = orderProduct.getName();
        }
    }
}
