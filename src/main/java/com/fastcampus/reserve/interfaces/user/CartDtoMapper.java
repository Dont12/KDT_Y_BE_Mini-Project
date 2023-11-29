package com.fastcampus.reserve.interfaces.user;

import com.fastcampus.reserve.domain.user.dto.request.CartItemAddDto;
import com.fastcampus.reserve.domain.user.dto.request.CartItemDeleteDto;
import com.fastcampus.reserve.domain.user.dto.response.CartDetailItemDto;
import com.fastcampus.reserve.domain.user.dto.response.CartListDto;
import com.fastcampus.reserve.domain.user.dto.response.PageDto;
import com.fastcampus.reserve.interfaces.user.dto.request.CartItemAddRequest;
import com.fastcampus.reserve.interfaces.user.dto.request.CartItemDeleteRequest;
import com.fastcampus.reserve.interfaces.user.dto.response.CartDetailItemResponse;
import com.fastcampus.reserve.interfaces.user.dto.response.CartListResponse;
import com.fastcampus.reserve.interfaces.user.dto.response.PageResponse;
import java.util.List;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CartDtoMapper {

    CartItemAddDto of(CartItemAddRequest request);

    default CartListResponse of(CartListDto dto) {
        return new CartListResponse(
            of(dto.page()),
            dto.totalPrice(),
            of(dto.items())
        );
    }

    default List<CartDetailItemResponse> of(List<CartDetailItemDto> items) {
        return items.stream().map(this::of).toList();
    }

    CartDetailItemResponse of(CartDetailItemDto cartDetailItemDto);

    PageResponse of(PageDto dto);

    CartItemDeleteDto of(CartItemDeleteRequest request);
}
