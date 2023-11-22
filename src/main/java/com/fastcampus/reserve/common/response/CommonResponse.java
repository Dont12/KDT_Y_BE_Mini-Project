package com.fastcampus.reserve.common.response;

import lombok.Getter;

@Getter
public final class CommonResponse<T> {

    private final Status status;
    private final String errorMessage;
    private final T data;

    private CommonResponse(Status status, String errorMessage, T data) {
        this.status = status;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    public static CommonResponse<Void> ok() {
        return new CommonResponse<>(Status.SUCCESS, null, null);
    }

    public static <T> CommonResponse<T> ok(T data) {
        return new CommonResponse<>(Status.SUCCESS, null, data);
    }

    public static CommonResponse<Void> fail(String errorMessage) {
        return new CommonResponse<>(Status.FAIL, errorMessage, null);
    }

    public static <T> CommonResponse<T> fail(String errorMessage, T data) {
        return new CommonResponse<>(Status.FAIL, errorMessage, data);
    }

    public static CommonResponse<Void> error(String errorMessage) {
        return new CommonResponse<>(Status.ERROR, errorMessage, null);
    }

    public static <T> CommonResponse<T> error(String errorMessage, T data) {
        return new CommonResponse<>(Status.ERROR, errorMessage, data);
    }

    public enum Status {
        SUCCESS, FAIL, ERROR
    }
}
