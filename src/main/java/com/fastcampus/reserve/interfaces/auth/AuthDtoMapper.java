package com.fastcampus.reserve.interfaces.auth;

import com.fastcampus.reserve.domain.auth.dto.request.LoginDto;
import com.fastcampus.reserve.domain.auth.dto.response.LoginTokenDto;
import com.fastcampus.reserve.interfaces.auth.dto.request.LoginRequest;
import com.fastcampus.reserve.interfaces.auth.dto.response.LoginResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AuthDtoMapper {

    LoginDto of(LoginRequest request);

    LoginResponse of(LoginTokenDto dto);
}
