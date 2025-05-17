package com.jobfinder.job_finder.controller;

import com.jobfinder.job_finder.dto.*;
import com.jobfinder.job_finder.dto.request.DtoRegister;
import com.jobfinder.job_finder.dto.request.UserLogin;
import com.jobfinder.job_finder.dto.response.ApiResponse;
import com.jobfinder.job_finder.entity.User;
import com.jobfinder.job_finder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // Đăng ký
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@RequestBody DtoRegister dtoRegister) {
        ApiResponse<?> response = userService.registerUser(dtoRegister); // Gọi method với ApiResponse trả về
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Đăng nhập

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody UserLogin userLogin) {
        try {
            ApiResponse<?> response = userService.loginUser(userLogin); // Gọi method với ApiResponse trả về
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            // Trả về lỗi nếu có sự cố
            ApiResponse<?> response = new ApiResponse<>(400, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    // Cập nhật hồ sơ
    @PutMapping("/profile/{userId}")
    public ResponseEntity<ApiResponse<?>> updateProfile(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        try {
            User updatedUser = userService.updateProfile(userId, userDTO);
            return ResponseEntity.ok(new ApiResponse<>(200, "Profile updated successfully", updatedUser));
        } catch (Exception e) {
            // Trả về lỗi nếu không tìm thấy người dùng hoặc có sự cố khác
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "User not found: " + e.getMessage(), null));
        }
    }

    // Xem hồ sơ người dùng
    @GetMapping("/profile/{userId}")
    public ResponseEntity<ApiResponse<?>> getUserProfile(@PathVariable Long userId) {
        try {
            User user = userService.getUserProfile(userId);

            if (user == null) {
                // Nếu không tìm thấy người dùng, trả về lỗi 404
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(404, "User not found", null));
            }

            // Trả về phản hồi thành công
            return ResponseEntity.ok(new ApiResponse<>(200, "User profile fetched successfully", user));
        } catch (Exception e) {
            // Xử lý ngoại lệ nếu có lỗi xảy ra
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "An error occurred while fetching the user profile: " + e.getMessage(), null));
        }
    }
    // Lấy tất cả hồ sơ người dùng
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<?>> getAllUserProfiles() {
        try {
            List<User> users = userService.getAllUserProfiles();

            // Trả về phản hồi thành công
            return ResponseEntity.ok(new ApiResponse<>(200, "All user profiles fetched successfully", users));
        } catch (Exception e) {
            // Xử lý ngoại lệ nếu có lỗi xảy ra
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "An error occurred while fetching user profiles: " + e.getMessage(), null));
        }
    }

}