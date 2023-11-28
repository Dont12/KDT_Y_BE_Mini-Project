package com.fastcampus.reserve.interfaces.product;

import com.fastcampus.reserve.domain.product.Product;
import com.fastcampus.reserve.domain.product.ProductImage;
import com.fastcampus.reserve.interfaces.product.dto.response.ProductResponse;
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

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "minPrice", target = "minPrice")
    @Mapping(source = "images", target = "imageUrl", qualifiedByName = "imageUrl")
    ProductResponse toEntity(Product product);

    // 이미지 URL을 추출하는 매핑 메서드 정의
    @Named("imageUrl")
    default String imageUrl(List<ProductImage> images) {
        if (images == null || images.isEmpty()) {
            return null; // 이미지가 없는 경우 null 반환
        }
        return images.get(0).getUrl(); // 첫 번째 이미지의 URL 반환
    }
}
