package com.acme.notes.shared;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.List;

/** The single response envelope for every endpoint. Clients unwrap {data} and read {pagination}. */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(boolean success, T data, PageMeta pagination, List<ApiError> errors, Instant timestamp) {

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, data, null, null, Instant.now());
    }

    public static <T> ApiResponse<T> ok(T data, PageMeta pagination) {
        return new ApiResponse<>(true, data, pagination, null, Instant.now());
    }

    public static <T> ApiResponse<T> fail(String code, String message) {
        return new ApiResponse<>(false, null, null, List.of(new ApiError(code, message)), Instant.now());
    }

    public record ApiError(String code, String message) {}
}
