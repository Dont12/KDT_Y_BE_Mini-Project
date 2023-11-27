package com.fastcampus.reserve.interfaces.user;

import com.fastcampus.reserve.application.user.UserFacade;
import com.fastcampus.reserve.common.response.CommonResponse;
import com.fastcampus.reserve.interfaces.user.dto.request.SignupRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
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
}
