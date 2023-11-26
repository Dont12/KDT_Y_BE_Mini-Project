package com.fastcampus.reserve.interfaces.auth.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
    @NotEmpty(message = "email은 필수값입니다.")
    String email,

    @NotEmpty(message = "password는 필수값입니다.")
    String password
) {
}
