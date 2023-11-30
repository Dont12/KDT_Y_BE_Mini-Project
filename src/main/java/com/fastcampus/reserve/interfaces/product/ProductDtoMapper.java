package com.fastcampus.reserve.interfaces.product;

import com.fastcampus.reserve.domain.product.dto.response.ProductDetailDto;
import com.fastcampus.reserve.domain.product.dto.response.ProductSummaryDto;
import com.fastcampus.reserve.interfaces.product.dto.response.ProductDetailResponse;
import com.fastcampus.reserve.interfaces.product.dto.response.ProductResponse;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ProductDtoMapper {

    default List<ProductResponse> of(List<ProductSummaryDto> dtos) {
        if (dtos == null) {
            return null;
        }

        List<ProductResponse> list = new ArrayList<>();
        for (ProductSummaryDto dto : dtos) {
            list.add(of(dto));
        }
        return list;
    }

    ProductResponse of(ProductSummaryDto dto);

    ProductDetailResponse of(ProductDetailDto dto);
}
