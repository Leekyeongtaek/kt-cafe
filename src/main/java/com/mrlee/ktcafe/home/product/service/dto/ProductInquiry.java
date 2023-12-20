package com.mrlee.ktcafe.home.product.service.dto;

import com.mrlee.ktcafe.home.product.domain.ProductOption;
import com.mrlee.ktcafe.home.product.domain.ProductOptionGroup;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.mrlee.ktcafe.home.product.domain.Product;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductInquiry {

    private Long productId;
    private String name;
    private int basicPrice;
    private String description;
    private String mainImage;
    private String addImage1;
    private String addImage2;
    private List<ProductOptionGroupInquiry> productOptionGroupInquiryList;


    public ProductInquiry(Product product) {
        this.productId = product.getId();
        this.name = product.getName();
        this.basicPrice = product.getBasicPrice();
        this.description = product.getDescription();
        this.mainImage = product.getMainImage();
        this.addImage1 = product.getAddImage1();
        this.addImage2 = product.getAddImage2();
        this.productOptionGroupInquiryList = product.getProductOptionGroups().stream().map(ProductOptionGroupInquiry::new).toList();
    }

    @Data
    @NoArgsConstructor
    public static class ProductOptionGroupInquiry {

        private long productOptionGroupId;
        private String name;
        private List<ProductOptionInquiry> productOptionInquiryList;

        public ProductOptionGroupInquiry(ProductOptionGroup productOptionGroup) {
            this.productOptionGroupId = productOptionGroup.getId();
            this.name = productOptionGroup.getName();
            this.productOptionInquiryList = productOptionGroup.getProductOptions().stream().map(ProductOptionInquiry::new).toList();
        }

        @Data
        @NoArgsConstructor
        public static class ProductOptionInquiry {

            private long productOptionId;
            private String name;
            private int price;

            public ProductOptionInquiry(ProductOption productOption) {
                this.productOptionId = productOption.getId();
                this.name = productOption.getName();
                this.price = productOption.getPrice();
            }
        }
    }
}
