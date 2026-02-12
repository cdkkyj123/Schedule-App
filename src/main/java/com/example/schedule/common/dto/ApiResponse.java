package com.example.schedule.common.dto;

public record ApiResponse<T> (
        String status,
        String massage,
        T data
) {
    // 성공 응답 (데이터 있음)
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("success", null, data);
    }

    // 성공 응답 (데이터 없음)
    public static <T> ApiResponse<T> successWithNoContent() {
        return new ApiResponse<>("success", null, null);
    }

    // 실패 응답
    public static <T> ApiResponse<T> error(String massage) {
        return new ApiResponse<>("error", massage, null);
    }
}
