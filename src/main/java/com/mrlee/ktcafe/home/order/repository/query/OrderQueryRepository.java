package com.mrlee.ktcafe.home.order.repository.query;

import com.mrlee.ktcafe.home.order.domain.*;
import com.mrlee.ktcafe.home.order.service.dto.OrderSearchCond;
import com.querydsl.core.types.dsl.BooleanExpression;
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

        List<Order> result = queryFactory.select(order)
                .from(order)
                .leftJoin(order.payment, payment)
                .fetchJoin()
                .leftJoin(order.review, review)
                .fetchJoin()
                .join(order.orderProducts, orderProduct)
                .where(
                        order.memberId.eq(memberId),
                        orderProductNameEq(orderSearchCond.getProductName())
                )
                .orderBy(order.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = queryFactory.select(order.count())
                .from(order)
                .join(order.orderProducts, orderProduct)
                .where(
                        order.memberId.eq(memberId),
                        orderProductNameEq(orderSearchCond.getProductName())
                )
                .fetchOne();

        List<OrderQueryInquiryList> content = result.stream()
                .map(OrderQueryInquiryList::new)
                .toList();

        return new PageImpl<>(content, pageable, totalCount);
    }

    private BooleanExpression orderProductNameEq(String name) {
        return StringUtils.hasText(name) ? orderProduct.name.contains(name) : null;
    }
}
