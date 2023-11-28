package com.fastcampus.reserve.interfaces.user;

import com.fastcampus.reserve.domain.user.dto.request.CartItemAddDto;
import com.fastcampus.reserve.interfaces.user.dto.request.CartItemAddRequest;
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
}
