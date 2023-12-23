package com.mrlee.ktcafe.home.order.service;

import com.mrlee.ktcafe.home.order.domain.Order;
import com.mrlee.ktcafe.home.order.repository.OrderRepository;
import com.mrlee.ktcafe.home.order.repository.ReviewRepository;
import com.mrlee.ktcafe.home.order.repository.query.OrderQueryRepository;
import com.mrlee.ktcafe.home.order.repository.query.dto.OrderQueryInquiryList;
import com.mrlee.ktcafe.home.order.service.dto.*;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mrlee.ktcafe.home.order.OrderFixture.anCreateReviewInput;
import static com.mrlee.ktcafe.home.order.OrderFixture.anUpdateReviewInput;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@Sql(value = "/db/sql/orders/orders.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional
@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderQueryRepository orderQueryRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private EntityManager em;

    @Test
    void 주문결제() {
        Order order = orderRepository.findById(1L).get();

        orderService.payOrder(order.getId());

        assertThat(order.getOrderStatus()).isEqualTo(Order.OrderStatus.PAYED);
    }

    @Test
    void 주문취소() {
        Order order = orderRepository.findById(1L).get();

        orderService.cancelOrder(order.getId());

        assertThat(order.getOrderStatus()).isEqualTo(Order.OrderStatus.CANCEL);
    }

    @Test
    void 상세조회() {
        OrderInquiry orderInquiry = orderService.findOrder(1L);

        assertThat(orderInquiry.getOrderSummary().getOrderId()).isEqualTo(1L);
    }

    @Test
    void 목록_검색() {
        Long memberId = 1L;
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<OrderQueryInquiryList> orderQueryInquiryLists = orderQueryRepository.searchOrder(memberId, pageRequest, new OrderSearchCond());
        List<OrderQueryInquiryList> result = orderQueryInquiryLists.getContent();

        assertThat(result.size()).isEqualTo(2);
    }

        @Test
    void 목록_검색시_주문상품을_조건으로_검색할_수_있다() {
        Long memberId = 1L;
        PageRequest pageRequest = PageRequest.of(0, 10);
        OrderSearchCond orderSearchCond = new OrderSearchCond("라떼");

        Page<OrderQueryInquiryList> resultPage = orderQueryRepository.searchOrder(memberId, pageRequest, orderSearchCond);
        List<OrderQueryInquiryList> result = resultPage.getContent();

        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void 결제완료가_아닌_주문은_리뷰_등록시_예외_발생() {
        CreateReviewInput createReviewInput = anCreateReviewInput().orderId(1L).memberId(1L).build();

        assertThatThrownBy(() -> orderService.addReview(createReviewInput))
                .isInstanceOf(IllegalArgumentException.class).hasMessage("결제완료 주문만 리뷰를 작성할 수 있습니다.");
    }

    @Test
    void 리뷰_등록시_주문자_정보가_다른_경우_예외_발생() {
        Order order = orderRepository.findById(1L).get();
        order.payed();

        CreateReviewInput createReviewInput = anCreateReviewInput().orderId(1L).memberId(2L).build();

        assertThatThrownBy(() -> orderService.addReview(createReviewInput))
                .isInstanceOf(IllegalArgumentException.class).hasMessage("주문자 정보가 다릅니다.");
    }

    @Test
    void 리뷰_수정시_주문자_정보가_다른_경우_예외_발생() {
        UpdateReviewInput updateReviewInput = anUpdateReviewInput().memberId(2L).build();

        assertThatThrownBy(() -> orderService.modifyReview(1L, updateReviewInput))
                .isInstanceOf(IllegalArgumentException.class).hasMessage("주문자 정보가 다릅니다.");
    }

    @Test
    void 리뷰_조회() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<ReviewInquiryList> resultPage = orderService.findAllReview(pageRequest);
        List<ReviewInquiryList> result = resultPage.getContent();

        assertThat(result.size()).isEqualTo(2);
    }
}