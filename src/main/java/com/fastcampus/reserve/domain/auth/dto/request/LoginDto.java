package com.fastcampus.reserve.domain.auth.dto.request;

public record LoginDto(
    String email,
    String password
) {
}
