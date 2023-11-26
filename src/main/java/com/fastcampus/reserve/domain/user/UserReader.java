package com.fastcampus.reserve.domain.user;

public interface UserReader {

    User findByEmail(String email);

    boolean existsByEmail(String email);
}
