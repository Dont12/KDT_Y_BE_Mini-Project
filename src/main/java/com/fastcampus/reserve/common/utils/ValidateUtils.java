package com.fastcampus.reserve.common.utils;

import com.fastcampus.reserve.common.exception.CustomException;
import com.fastcampus.reserve.common.response.ErrorCode;
import java.util.List;
import java.util.Objects;

public final class ValidateUtils {

    private ValidateUtils() {
    }

    public static void nullCheck(Object param) {
        if (Objects.isNull(param)) {
            throw new CustomException(ErrorCode.COMMON_INVALID_PARAMETER);
        }
    }

    public static void emptyCheck(List<?> param) {
        if (param.isEmpty()) {
            throw new CustomException(ErrorCode.COMMON_INVALID_PARAMETER);
        }
    }
}
