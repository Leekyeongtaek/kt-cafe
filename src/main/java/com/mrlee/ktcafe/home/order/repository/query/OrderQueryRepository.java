package com.mrlee.ktcafe.home.order.repository.query;

import com.mrlee.ktcafe.home.order.domain.*;
import com.mrlee.ktcafe.home.order.service.dto.OrderSearchCond;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import com.mrlee.ktcafe.home.order.repository.query.dto.OrderQueryInquiryList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.mrlee.ktcafe.home.order.domain.QOrder.order;
import static com.mrlee.ktcafe.home.order.domain.QOrderProduct.orderProduct;
import static com.mrlee.ktcafe.home.order.domain.QPayment.payment;
import static com.mrlee.ktcafe.home.order.domain.QReview.review;

@RequiredArgsConstructor
@Repository
public class OrderQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<OrderQueryInquiryList> searchOrder(Long memberId, Pageable pageable, OrderSearchCond orderSearchCond) {
        if (StringUtils.hasText(orderSearchCond.getProductName())) {
            return searchOrderWithOrderProductName(memberId, pageable, orderSearchCond);
        } else {
            return searchOrderSimple(memberId, pageable);
        }
    }

    private PageImpl<OrderQueryInquiryList> searchOrderSimple(Long memberId, Pageable pageable) {
        List<Order> result = queryFactory.select(order)
                .from(order)
                .leftJoin(order.payment, payment)
                .fetchJoin()
                .leftJoin(order.review, review)
                .fetchJoin()
                .where(order.memberId.eq(memberId))
                .orderBy(order.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = queryFactory.select(order.count())
                .from(order)
                .where(order.memberId.eq(memberId))
                .fetchOne();

        List<OrderQueryInquiryList> content = result.stream()
                .map(OrderQueryInquiryList::new)
                .toList();

        return new PageImpl<>(content, pageable, totalCount);
    }

    private PageImpl<OrderQueryInquiryList> searchOrderWithOrderProductName(Long memberId, Pageable pageable, OrderSearchCond orderSearchCond) {
        QOrder subOrder = new QOrder("subOrder");

        List<Order> result = queryFactory.selectFrom(order)
                .leftJoin(order.payment, payment)
                .fetchJoin()
                .leftJoin(order.review, review)
                .fetchJoin()
                .where(order.id.in(
                        JPAExpressions
                                .selectDistinct(subOrder.id)
                                .from(subOrder)
                                .join(subOrder.orderProducts, orderProduct)
                                .where(
                                        subOrder.memberId.eq(memberId),
                                        orderProduct.name.contains(orderSearchCond.getProductName()))
                ))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(order.id.desc())
                .fetch();

        Long totalCount = queryFactory.select(order.count())
                .from(order)
                .where(order.id.in(
                        JPAExpressions
                                .selectDistinct(subOrder.id)
                                .from(subOrder)
                                .join(subOrder.orderProducts, orderProduct)
                                .where(subOrder.memberId.eq(memberId),
                                        orderProduct.name.contains(orderSearchCond.getProductName()))
                )).fetchOne();

        List<OrderQueryInquiryList> content = result.stream()
                .map(OrderQueryInquiryList::new)
                .toList();

        return new PageImpl<>(content, pageable, totalCount);
    }
}
