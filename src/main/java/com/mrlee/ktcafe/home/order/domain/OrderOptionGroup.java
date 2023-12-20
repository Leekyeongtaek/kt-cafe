package com.mrlee.ktcafe.home.order.domain;

import com.mrlee.ktcafe.common.BaseTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderOptionGroup extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_option_group_id")
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_product_id")
    private OrderProduct orderProduct;

    @ElementCollection
    @CollectionTable(name = "order_option", joinColumns = @JoinColumn(name = "order_option_group_id"))
    private List<OrderOption> orderOptions = new ArrayList<>();

    @Builder
    public OrderOptionGroup(Long id, String name, List<OrderOption> orderOptions) {
        this.id = id;
        this.name = name;
        this.orderOptions = orderOptions;
    }

    void mappingOrderProduct(OrderProduct orderProduct) {
        this.orderProduct = orderProduct;
    }

    int calculateTotalAmount() {
        return orderOptions.stream().mapToInt(OrderOption::getPrice).sum();
    }
}
