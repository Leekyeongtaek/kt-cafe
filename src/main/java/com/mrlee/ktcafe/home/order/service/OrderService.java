package com.mrlee.ktcafe.home.order.service;

import com.mrlee.ktcafe.home.member.domain.Member;
import com.mrlee.ktcafe.home.member.repository.MemberRepository;
import com.mrlee.ktcafe.home.order.domain.*;
import com.mrlee.ktcafe.home.order.repository.OrderRepository;
import com.mrlee.ktcafe.home.order.repository.PaymentRepository;
import com.mrlee.ktcafe.home.order.repository.ReviewRepository;
import com.mrlee.ktcafe.home.order.repository.query.OrderQueryRepository;
import com.mrlee.ktcafe.home.order.repository.query.dto.OrderQueryInquiryList;
import com.mrlee.ktcafe.home.order.service.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final OrderMapper orderMapper;
    private final OrderValidator orderValidator;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final PaymentCreator paymentCreator;

    public void addOrder(ShoppingCartInput shoppingCartInput) {
        Order order = orderMapper.unpackShoppingCart(shoppingCartInput);
        order.placeOrder(orderValidator);
        Payment payment = paymentCreator.createPayment(order, shoppingCartInput.getPaymentMethod());
        order.mappingPayment(payment);
        paymentRepository.save(payment);
        orderRepository.save(order);
    }

    public void payOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);
        order.payed();
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);
        order.canceled();
    }

    public OrderInquiry findOrder(Long orderId) {
        Order order = orderRepository.findByIdPaymentAndReviewJoinFetch(orderId).orElseThrow(IllegalArgumentException::new);
        return new OrderInquiry(order);
    }

    public Page<OrderQueryInquiryList> searchOrder(Long memberId, Pageable pageable, OrderSearchCond orderSearchCond) {
        return orderQueryRepository.searchOrder(memberId, pageable, orderSearchCond);
    }

    public void addReview(CreateReviewInput createReviewInput) {
        Order order = orderRepository.findById(createReviewInput.getOrderId()).orElseThrow(IllegalArgumentException::new);
        Member member = memberRepository.findById(createReviewInput.getMemberId()).orElseThrow(IllegalArgumentException::new);

        if (!order.isPayed()) {
            throw new IllegalArgumentException("결제완료 주문만 리뷰를 작성할 수 있습니다.");
        }

        if (!order.isOrderMember(createReviewInput.getMemberId())) {
            throw new IllegalArgumentException("주문자 정보가 다릅니다.");
        }

        Review review = Review.createReview(order, member, createReviewInput.getRating(), createReviewInput.getContents(), createReviewInput.getPhotoImage());

        try {
            reviewRepository.save(review);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("주문당 리뷰는 하나만 작성이 가능합니다.", e);
        }
    }

    public void modifyReview(Long reviewId, UpdateReviewInput updateReviewInput) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(IllegalArgumentException::new);

        if (!review.isReviewMember(updateReviewInput.getMemberId())) {
            throw new IllegalArgumentException("주문자 정보가 다릅니다.");
        }

        review.update(updateReviewInput);
    }

    public Page<ReviewInquiryList> findAllReview(Pageable pageable) {
        Page<Review> reviewPage = reviewRepository.findAllMemberJoinFetch(pageable);
        List<ReviewInquiryList> content = reviewPage.getContent().stream().map(ReviewInquiryList::new).toList();
        return new PageImpl<>(content, pageable, reviewPage.getTotalElements());
    }
}
