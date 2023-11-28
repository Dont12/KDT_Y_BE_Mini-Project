package com.fastcampus.reserve.domain.user;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartReader {

    Page<Cart> getCartItems(User user, Pageable pageable);
}
