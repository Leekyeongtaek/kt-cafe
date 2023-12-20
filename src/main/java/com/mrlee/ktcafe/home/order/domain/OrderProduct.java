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
public class OrderProduct extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id")
    private Long id;
    private Long productId;
    private String name;
    private int price;
    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToMany(mappedBy = "orderProduct", cascade = CascadeType.ALL)
    private List<OrderOptionGroup> orderOptionGroups = new ArrayList<>();

    @Builder
    public OrderProduct(Long id, Long productId, String name, int price, int count, List<OrderOptionGroup> orderOptionGroups) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.count = count;
        for (OrderOptionGroup orderOptionGroup : orderOptionGroups) {
            mappingOrderOptionGroup(orderOptionGroup);
        }
    }

    void mappingOrder(Order order) {
        this.order = order;
    }

    private void mappingOrderOptionGroup(OrderOptionGroup orderOptionGroup) {
        this.orderOptionGroups.add(orderOptionGroup);
        orderOptionGroup.mappingOrderProduct(this);
    }

    public int calculateTotalAmount() {
        return price * count;
    }

    public int calculateTotalAmountWithOrderOption() {
        int totalOptionAmount = orderOptionGroups.stream().mapToInt(OrderOptionGroup::calculateTotalAmount).sum();
        return (price + totalOptionAmount) * count;
    }
}
