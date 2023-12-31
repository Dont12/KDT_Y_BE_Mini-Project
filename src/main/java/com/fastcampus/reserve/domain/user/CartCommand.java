package com.fastcampus.reserve.domain.user;

public interface CartCommand {

    void store(Cart cart);

    void deleteById(Long id);

    boolean isValid(User user, Long cartId);
}
