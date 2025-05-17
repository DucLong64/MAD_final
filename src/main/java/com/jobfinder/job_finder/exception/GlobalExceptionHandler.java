package com.jobfinder.job_finder.exception;

import com.jobfinder.job_finder.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


public class GlobalExceptionHandler {

    // Xử lý mọi ngoại lệ chung
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        ApiResponse<?> response = new ApiResponse<>(500, "Internal Server Error: " + e.getMessage(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    // Xử lý ngoại lệ khi không tìm thấy người dùng
//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<ApiResponse<?>> handleUserNotFoundException(UserNotFoundException e) {
//        ApiResponse<?> response = new ApiResponse<>(404, "User not found: " + e.getMessage(), null);
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//    }

    // Xử lý ngoại lệ về xác thực (ví dụ: thông tin đăng nhập không hợp lệ)
//    @ExceptionHandler(InvalidCredentialsException.class)
//    public ResponseEntity<ApiResponse<?>> handleInvalidCredentialsException(InvalidCredentialsException e) {
//        ApiResponse<?> response = new ApiResponse<>(401, "Invalid credentials: " + e.getMessage(), null);
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//    }

    // Xử lý ngoại lệ khi có vấn đề với dữ liệu đầu vào (ví dụ: thiếu trường dữ liệu)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationExceptions(MethodArgumentNotValidException e) {
        StringBuilder errorMessage = new StringBuilder();
        e.getBindingResult().getAllErrors().forEach(error -> {
            errorMessage.append(error.getDefaultMessage()).append(" ");
        });
        ApiResponse<?> response = new ApiResponse<>(400, "Validation error: " + errorMessage.toString(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Xử lý ngoại lệ về lỗi mã hóa mật khẩu
//    @ExceptionHandler(BCryptPasswordEncoderException.class)
//    public ResponseEntity<ApiResponse<?>> handleBCryptPasswordEncoderException(BCryptPasswordEncoderException e) {
//        ApiResponse<?> response = new ApiResponse<>(500, "Password encoding error: " + e.getMessage(), null);
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//    }

    // Xử lý các ngoại lệ khác có thể thêm vào nếu cần
}
