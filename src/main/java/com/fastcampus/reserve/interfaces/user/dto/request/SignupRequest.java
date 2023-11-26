package com.fastcampus.reserve.interfaces.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignupRequest(
    @NotEmpty(message = "email은 필수값입니다.")
    @Email
    String email,

    @NotEmpty(message = "password는 필수값입니다.")
    @Size(min = 6, message = "최소 6자 이상이어야 합니다.")
    String password,

    @NotEmpty(message = "nickname은 필수값입니다.")
    String nickname,

    @NotEmpty(message = "phone은 필수값입니다.")
    @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "000-0000-0000 형식으로 입력하셔야 합니다.")
    String phone
) {

}
