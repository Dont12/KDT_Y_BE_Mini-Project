package com.fastcampus.reserve.interfaces.order.dto.request;

import static com.fastcampus.reserve.common.response.ErrorCode.COMMON_INVALID_PARAMETER;

import com.fastcampus.reserve.common.exception.CustomException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record RegisterOrderItemRequest(
        @NotNull(message = "숙소 ID 는 필수 값 입니다.")
        Long productId,

        @NotNull(message = "방 ID 는 필수 값 입니다.")
        Long roomId,

        @NotNull(message = "체크인 날짜는 필수 값 입니다.")
        LocalDate checkInDate,

        @NotNull(message = "체크인 시간은 필수 값 입니다.")
        String checkInTime,

        @NotNull(message = "체크아웃 날짜는 필수 값 입니다.")
        LocalDate checkOutDate,

        @NotNull(message = "체크아웃 시간은 필수 값 입니다.")
        String checkOutTime,

        @NotNull(message = "숙박 인원은 필수 값 입니다.")
        @Min(value = 0, message = "숙박 인원은 0명 이상 값 입니다.")
        Integer guestCount,

        @NotNull(message = "예약 가격은 필수 값 입니다.")
        @Min(value = 0, message = "예약 가격은 0원 이상 값 입니다.")
        Integer price
) {

    public RegisterOrderItemRequest {
        if (checkInDate.isEqual(checkOutDate) || checkInDate.isAfter(checkOutDate)) {
            throw new CustomException(COMMON_INVALID_PARAMETER);
        }
    }
}
