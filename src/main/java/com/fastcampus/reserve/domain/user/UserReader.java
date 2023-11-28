package com.fastcampus.reserve.domain.user;

public interface UserReader {

    User findById(Long id);

    User findByEmail(String email);

    boolean existsByEmail(String email);
}
