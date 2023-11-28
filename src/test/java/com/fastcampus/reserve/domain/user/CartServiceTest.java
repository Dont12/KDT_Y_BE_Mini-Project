package com.fastcampus.reserve.domain.user;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.fastcampus.reserve.common.exception.CustomException;
import com.fastcampus.reserve.common.response.ErrorCode;
import com.fastcampus.reserve.domain.user.dto.request.CartItemAddDto;
import com.fastcampus.reserve.domain.user.dto.request.CartItemDeleteDto;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartCommand cartCommand;

    @InjectMocks
    private CartService cartService;

    @Test
    @DisplayName("입력에 문제가 없을 경우 예외 발생하지 않음")
    void addItem_success() {
        // given
        User user = User.builder().build();
        CartItemAddDto dto = new CartItemAddDto(
            1L,
            LocalDate.of(2010, 1, 1),
            LocalDate.of(2010, 1, 2),
            4
        );

        // when, then
        assertThatNoException()
            .isThrownBy(() -> cartService.addItem(user, dto));
    }

    @Test
    @DisplayName("체크아웃이 체크인보다 앞설 경우 예외")
    void addItem_fail_for_date() {
        // given
        User user = User.builder().build();
        CartItemAddDto dto = new CartItemAddDto(
            1L,
            LocalDate.of(2010, 1, 2),
            LocalDate.of(2010, 1, 1),
            4
        );

        // when, then
        assertThatThrownBy(() -> cartService.addItem(user, dto))
            .isInstanceOf(CustomException.class)
            .hasMessage(ErrorCode.INVALID_CHECK_IN_OUT_DATE.getMessage());
    }

    @Test
    @DisplayName("삭제할 장바구니 아이템이 존재하지 않으면 예외")
    void deleteItems_not_exist_item() {
        // given
        User user = User.builder().build();
        Long id = 1L;
        CartItemDeleteDto dto = new CartItemDeleteDto(List.of(id));

        // when, then
        assertThatThrownBy(() -> cartService.deleteItems(user, dto))
            .isInstanceOf(CustomException.class)
            .hasMessage(
                String.format("id: %d (%s)", id, ErrorCode.INVALID_CART_ITEM.getMessage())
            );
    }

    @Test
    @DisplayName("장바구니 아이템들 삭제 성공")
    void deleteItems() {
        // given
        User user = User.builder().build();
        Long id = -1L;
        CartItemDeleteDto dto = new CartItemDeleteDto(List.of(id));
        when(cartCommand.isValid(any(User.class), anyLong())).thenReturn(true);

        // when, then
        assertThatNoException()
            .isThrownBy(() -> cartService.deleteItems(user, dto));
    }
}
