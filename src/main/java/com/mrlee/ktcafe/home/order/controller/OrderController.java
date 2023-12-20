package com.mrlee.ktcafe.home.order.controller;

import com.mrlee.ktcafe.home.order.repository.query.dto.OrderQueryInquiryList;
import com.mrlee.ktcafe.home.order.service.OrderService;
import com.mrlee.ktcafe.home.order.service.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@Tag(name = "주문 API", description = "주문 관련 API")
@RequiredArgsConstructor
@RequestMapping("/orders")
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/")
    @Operation(summary = "주문 등록")
    public ResponseEntity<Void> ordersAdd(@RequestBody @Validated ShoppingCartInput shoppingCartInput) {
        orderService.addOrder(shoppingCartInput);
        return new ResponseEntity<>(OK);
    }

    @PatchMapping("/{orderId}")
    @Operation(summary = "주문 결제")
    @Parameter(name = "orderId", description = "주문 번호")
    public ResponseEntity<Void> orderPay(@PathVariable Long orderId) {
        orderService.payOrder(orderId);
        return new ResponseEntity<>(OK);
    }

    @PatchMapping("/{orderId}/cancel")
    @Operation(summary = "주문 취소")
    @Parameter(name = "orderId", description = "주문 번호")
    public ResponseEntity<Void> orderCancel(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return new ResponseEntity<>(OK);
    }

    @GetMapping("/{memberId}")
    @Operation(summary = "주문 기록 검색 및 조회")
    @Parameters({
            @Parameter(name = "pageable", hidden = true),
            @Parameter(name = "orderSearchCond", hidden = true),
            @Parameter(name = "memberId", description = "회원번호", example = "1"),
            @Parameter(name = "page", description = "페이지", example = "0"),
            @Parameter(name = "size", description = "사이즈", example = "10"),
            @Parameter(name = "productName", description = "주문한 상품 이름")
    })
    public ResponseEntity<Page<OrderQueryInquiryList>> orderList(@PathVariable Long memberId, Pageable pageable, OrderSearchCond orderSearchCond) {
        return new ResponseEntity<>(orderService.searchOrder(memberId, pageable, orderSearchCond), OK);
    }

    @GetMapping("/find/{orderId}")
    @Operation(summary = "주문 상세 정보 조회")
    @Parameter(name = "orderId", description = "주문 번호", example = "38")
    public ResponseEntity<OrderInquiry> orderDetails(@PathVariable Long orderId) {
        return new ResponseEntity<>(orderService.findOrder(orderId), OK);
    }

    @PostMapping("/review")
    @Operation(summary = "리뷰 등록", description = "리뷰는 주문당 하나만 작성이 가능하다.")
    public ResponseEntity<Void> reviewAdd(@RequestBody CreateReviewInput createReviewInput) {
        orderService.addReview(createReviewInput);
        return new ResponseEntity<>(OK);
    }

    @PatchMapping("/review/{reviewId}")
    @Operation(summary = "리뷰 수정")
    @Parameter(name = "reviewId", description = "리뷰 번호")
    public ResponseEntity<Void> reviewModify(@PathVariable Long reviewId, @RequestBody UpdateReviewInput updateReviewInput) {
        orderService.modifyReview(reviewId, updateReviewInput);
        return new ResponseEntity<>(OK);
    }

    @GetMapping("/review/")
    @Operation(summary = "리뷰 목록 조회")
    @Parameters({
            @Parameter(name = "pageable", hidden = true),
            @Parameter(name = "page", description = "페이지", example = "0"),
            @Parameter(name = "size", description = "사이즈", example = "10")
    })
    public ResponseEntity<Page<ReviewInquiryList>> reviewList(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(orderService.findAllReview(pageable), OK);
    }

}
