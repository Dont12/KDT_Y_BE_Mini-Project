package com.fastcampus.reserve.interfaces.user;

import com.fastcampus.reserve.domain.user.dto.request.CartItemAddDto;
import com.fastcampus.reserve.domain.user.dto.request.CartItemDeleteDto;
import com.fastcampus.reserve.interfaces.user.dto.request.CartItemAddRequest;
import com.fastcampus.reserve.interfaces.user.dto.request.CartItemDeleteRequest;
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

    CartItemDeleteDto of(CartItemDeleteRequest request);
}
