package com.fastcampus.reserve.interfaces.product;

import com.fastcampus.reserve.application.ProductFacade;
import com.fastcampus.reserve.common.response.CommonResponse;
import com.fastcampus.reserve.domain.product.dto.request.ProductListOptionDto;
import com.fastcampus.reserve.interfaces.product.dto.response.ProductDetailResponse;
import com.fastcampus.reserve.interfaces.product.dto.response.ProductResponse;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/products")
public class ProductApiController {

    private final ProductFacade productFacade;
    private final ProductDtoMapper mapper;

    @GetMapping
    public CommonResponse<List<ProductResponse>> getProducts(
            @RequestParam LocalDate checkIn,
            @RequestParam LocalDate checkOut,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String areaCode,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize
    ) {
        var requestDto = new ProductListOptionDto(
                checkIn, checkOut, category, areaCode, page, pageSize
        );

        var responseDto = productFacade.getProducts(requestDto);
        return CommonResponse.ok(mapper.of(responseDto));
    }

    @GetMapping("/{id}")
    public CommonResponse<ProductDetailResponse> getProductDetail(
            @RequestParam LocalDate checkIn,
            @RequestParam LocalDate checkOut,
            @PathVariable Long id
    ) {
        var dto = productFacade.getProductDetail(id, checkIn, checkOut);
        return CommonResponse.ok(mapper.of(dto));
    }
}
