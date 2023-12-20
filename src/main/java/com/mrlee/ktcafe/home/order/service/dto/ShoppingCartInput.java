package com.mrlee.ktcafe.home.order.service.dto;

import com.mrlee.ktcafe.home.order.domain.Payment;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ShoppingCartInput {

    @NotNull
    private Long memberId;

    @NotNull
    private Payment.PaymentMethod paymentMethod;

    @NotEmpty
    private List<CartItemInput> cartItemInputList = new ArrayList<>();

    @Data
    @NoArgsConstructor
    public static class CartItemInput {

        @NotEmpty
        private Long productId;
        @NotEmpty
        private String name;
        @Range(min = 100, max = 100000)
        private int price;
        @Range(min = 1, max = 1000)
        private int count;
        @Valid
        private List<CartOptionGroupInput> cartOptionGroupInputList = new ArrayList<>();

        @Data
        @NoArgsConstructor
        public static class CartOptionGroupInput {

            @NotEmpty
            private String name;
            @NotEmpty
            @Valid
            private List<CartOptionInput> cartOptionInputList = new ArrayList<>();
        }

        @Data
        @NoArgsConstructor
        public static class CartOptionInput {

            @NotEmpty
            private String name;
            @Range(min = 0, max = 100000)
            private int price;
        }
    }
}
