package com.fastcampus.reserve.interfaces.product;

import com.fastcampus.reserve.domain.dto.response.ProductDto;
import com.fastcampus.reserve.domain.product.Product;
import com.fastcampus.reserve.domain.product.ProductImage;
import com.fastcampus.reserve.interfaces.product.dto.response.ProductResponse;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ProductDtoMapper {

    default List<ProductResponse> of(List<ProductDto> dtos) {
        if (dtos == null) {
            return null;
        }

        List<ProductResponse> list = new ArrayList<>();
        for (ProductDto dto : dtos) {
            list.add(of(dto));
        }
        return list;
    }

    ProductResponse of(ProductDto dto);
}
