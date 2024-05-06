package com.social.net.common.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ApiResponse<T> {
    private T data;
    private Integer status;
    private ErrorResponse error;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<T>(data, 200, null);
    }

    public static <T> ApiResponse<T> fail(ErrorResponse errorResponse) {
        return new ApiResponse<T>(null, 400, errorResponse);
    }
}
