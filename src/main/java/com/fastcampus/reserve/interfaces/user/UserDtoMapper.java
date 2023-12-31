package com.fastcampus.reserve.interfaces.user;

import com.fastcampus.reserve.domain.user.dto.request.SignupDto;
import com.fastcampus.reserve.domain.user.dto.response.UserInfoDto;
import com.fastcampus.reserve.interfaces.user.dto.request.SignupRequest;
import com.fastcampus.reserve.interfaces.user.response.UserInfoResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface UserDtoMapper {

    SignupDto of(SignupRequest request);

    UserInfoResponse of(UserInfoDto dto);
}
