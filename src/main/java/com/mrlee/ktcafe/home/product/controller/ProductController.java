package com.mrlee.ktcafe.home.product.controller;

import com.mrlee.ktcafe.home.product.repository.query.dto.ProductQueryInquiryList;
import com.mrlee.ktcafe.home.product.service.ProductService;
import com.mrlee.ktcafe.home.product.service.dto.CreateProductInput;
import com.mrlee.ktcafe.home.product.service.dto.ProductInquiry;
import com.mrlee.ktcafe.home.product.service.dto.ProductSearchCond;
import com.mrlee.ktcafe.home.product.service.dto.UpdateProductInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@Tag(name = "상품 API", description = "상품 관련 API")
@RequiredArgsConstructor
@RequestMapping("/products")
@RestController
public class ProductController {

    private final ProductService productService;

    @PostMapping("/")
    @Operation(summary = "상품 신규 등록")
    public ResponseEntity<Void> productAdd(@RequestBody @Validated CreateProductInput createProductInput) {
        productService.addProduct(createProductInput);
        return new ResponseEntity<>(OK);
    }

    @PatchMapping("/{productId}")
    @Operation(summary = "상품 수정", description = "상품의 옵션그룹과 옵션은 기존 옵션은 수정, 신규 옵션은 추가한다.")
    @Parameter(name = "productId", example = "134", description = "상품 번호")
    public ResponseEntity<Void> productModify(@PathVariable Long productId, @RequestBody @Validated UpdateProductInput updateProductInput) {
        productService.modifyProduct(productId, updateProductInput);
        return new ResponseEntity<>(OK);
    }

    @GetMapping("/{productId}")
    @Operation(summary = "상품 상세 조회")
    @Parameter(name = "productId", example = "134", description = "상품 번호")
    public ResponseEntity<ProductInquiry> productDetails(@PathVariable Long productId) {
        return new ResponseEntity<>(productService.findProduct(productId), OK);
    }

    @GetMapping("/")
    @Operation(summary = "상품 검색 및 조회")
    @Parameters({
            @Parameter(name = "pageable", hidden = true),
            @Parameter(name = "productSearchCond", hidden = true),
            @Parameter(name = "page", description = "페이지", example = "0"),
            @Parameter(name = "size", description = "사이즈", example = "10"),
            @Parameter(name = "name", description = "상품명"),
            @Parameter(name = "minPrice", description = "최저가격"),
            @Parameter(name = "maxPrice", description = "최고가격"),
            @Parameter(name = "productType", description = "상품 타입",
                    examples = {@ExampleObject(name = "COFFEE"), @ExampleObject(name = "CAKE"), @ExampleObject(name = "BREAD"), @ExampleObject(name = "DRINK")})
    })
    public ResponseEntity<Page<ProductQueryInquiryList>> productList(Pageable pageable, ProductSearchCond productSearchCond) {
        return new ResponseEntity<>(productService.searchProduct(pageable, productSearchCond), OK);
    }

    @Operation(summary = "옵션 그룹 삭제")
    @DeleteMapping("/product-option-group/{productOptionGroupId}")
    public ResponseEntity<Void> productOptionGroupRemove(@PathVariable Long productOptionGroupId) {
        productService.removeProductOptionGroup(productOptionGroupId);
        return new ResponseEntity<>(OK);
    }

    @Operation(summary = "옵션 그룹의 옵션 삭제")
    @DeleteMapping("/product-option-group/{productOptionGroupId}/product-option/{productOptionId}")
    public ResponseEntity<Void> productOptionRemove(@PathVariable Long productOptionGroupId, @PathVariable Long productOptionId) {
        productService.removeProductOption(productOptionGroupId, productOptionId);
        return new ResponseEntity<>(OK);
    }
}
