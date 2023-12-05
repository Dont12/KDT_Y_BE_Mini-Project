package com.fastcampus.reserve.domain.user;

public interface UserCommand {

    void store(User user);

    void deleteById(Long userId);
}
