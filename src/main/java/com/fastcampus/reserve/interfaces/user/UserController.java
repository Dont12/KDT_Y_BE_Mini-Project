package com.fastcampus.reserve.interfaces.user;

import com.fastcampus.reserve.application.user.UserFacade;
import com.fastcampus.reserve.common.response.CommonResponse;
import com.fastcampus.reserve.common.security.PrincipalDetails;
import com.fastcampus.reserve.interfaces.user.dto.request.PasswordChangeRequest;
import com.fastcampus.reserve.interfaces.user.dto.request.SignupRequest;
import com.fastcampus.reserve.interfaces.user.response.UserInfoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

    private final UserFacade userFacade;
    private final UserDtoMapper mapper;

    @PostMapping
    public CommonResponse<Void> signup(
        @RequestBody @Valid SignupRequest request
    ) {
        userFacade.signup(mapper.of(request));
        return CommonResponse.ok();
    }

    @GetMapping
    public CommonResponse<UserInfoResponse> myInfo(
        @AuthenticationPrincipal PrincipalDetails principal
    ) {
        Long userId = principal.id();
        var userInfo = userFacade.getUserInfo(userId);

        return CommonResponse.ok(mapper.of(userInfo));
    }

    @PutMapping("/password")
    public CommonResponse<Void> changePassword(
        @AuthenticationPrincipal PrincipalDetails principal,
        @RequestBody PasswordChangeRequest request
    ) {
        Long userId = principal.id();
        userFacade.changePassword(userId, request.password());

        return CommonResponse.ok();
    }
}
