package com.fastcampus.reserve.common.utils;

import static com.fastcampus.reserve.common.response.ErrorCode.COMMON_INVALID_PARAMETER;

import com.fastcampus.reserve.common.exception.CustomException;
import java.util.List;
import java.util.Objects;

public final class ValidateUtils {

    private ValidateUtils() {
    }

    public static void nullCheck(Object... params) {
        for (Object param : params) {
            if (Objects.isNull(param)) {
                throw new CustomException(COMMON_INVALID_PARAMETER);
            }
        }
    }

    public static void emptyCheck(List<?> param) {
        if (param.isEmpty()) {
            throw new CustomException(COMMON_INVALID_PARAMETER);
        }
    }
}
