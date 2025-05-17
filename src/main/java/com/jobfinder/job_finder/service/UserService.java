package com.jobfinder.job_finder.service;

import com.jobfinder.job_finder.dto.*;
import com.jobfinder.job_finder.dto.request.DtoRegister;
import com.jobfinder.job_finder.dto.request.UserLogin;
import com.jobfinder.job_finder.dto.response.ApiResponse;
import com.jobfinder.job_finder.dto.response.UserDTOResponse;
import com.jobfinder.job_finder.entity.JobSeeker;
import com.jobfinder.job_finder.entity.Recruiter;
import com.jobfinder.job_finder.util.JwtUtil;
import com.jobfinder.job_finder.util.Role;
import com.jobfinder.job_finder.entity.User;
import com.jobfinder.job_finder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    // Đăng ký người dùng
    public ApiResponse<?> registerUser(DtoRegister dtoRegister) {
        // Kiểm tra nếu email đã tồn tại trong hệ thống
        if (userRepository.findByEmail(dtoRegister.getEmail()).isPresent()) {
            return new ApiResponse<>(400, "Email already in use", null);  // Trả về lỗi nếu email đã tồn tại
        }

        User user = new User();
        // Đăng ký theo vai trò
        if (dtoRegister.getRole() == Role.JOB_SEEKER) {
            user = new JobSeeker();  // Tạo JobSeeker cho người tìm việc
        } else if (dtoRegister.getRole() == Role.RECRUITER) {
            user = new Recruiter();  // Tạo Recruiter cho nhà tuyển dụng
        }

        user.setFullName(dtoRegister.getFullName());
        user.setEmail(dtoRegister.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(dtoRegister.getPassword()));  // Mã hóa mật khẩu
        user.setRole(dtoRegister.getRole());
        userRepository.save(user);

        // Trả về thông báo thành công
        return new ApiResponse<>(201, "User registered successfully", null);
    }

    // Đăng nhập người dùng
    public ApiResponse<?> loginUser(UserLogin userLogin) {
        User user = userRepository.findByEmail(userLogin.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!new BCryptPasswordEncoder().matches(userLogin.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        UserDTOResponse userDTOResponse = new UserDTOResponse(user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole().toString(),
                token);
        // Trả về ApiResponse với mã trạng thái 200, thông báo thành công và dữ liệu người dùng
        return new ApiResponse<>(200, "Login successful", userDTOResponse);
    }
    // Cập nhật hồ sơ người dùng
    public User updateProfile(Long userId, UserDTO userDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() == Role.JOB_SEEKER) {
            JobSeeker jobSeeker = (JobSeeker) user;
            jobSeeker.setFullName(userDTO.getFullName());
            jobSeeker.setProfilePicture(userDTO.getProfilePicture());
            jobSeeker.setPhoneNumber(userDTO.getPhoneNumber());
            jobSeeker.setBirthDate(userDTO.getBirthDate());
            jobSeeker.setWorkExperience(userDTO.getWorkExperience());
            jobSeeker.setEducation(userDTO.getEducation());
            jobSeeker.setSkills(userDTO.getSkills());
            jobSeeker.setLanguages(userDTO.getLanguages());
            jobSeeker.setCertifications(userDTO.getCertifications());
            jobSeeker.setCvFile(userDTO.getCvFile());
        } else if (user.getRole() == Role.RECRUITER) {
            Recruiter recruiter = (Recruiter) user;
            recruiter.setFullName(userDTO.getFullName());
            recruiter.setPhoneNumber(userDTO.getPhoneNumber());
            recruiter.setCompanyName(userDTO.getCompanyName());
            recruiter.setCompanyAddress(userDTO.getCompanyAddress());
            recruiter.setCompanyPhoneNumber(userDTO.getCompanyPhoneNumber());
            recruiter.setCompanyLogo(userDTO.getCompanyLogo());
        }
        return userRepository.save(user);
    }

    // Lấy thông tin hồ sơ người dùng
    public User getUserProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if(user instanceof JobSeeker) {
            JobSeeker jobSeeker = (JobSeeker) user;
            return jobSeeker;
        }
        else if(user instanceof Recruiter) {
            Recruiter recruiter = (Recruiter) user;
            return recruiter;
        }
        return user;
    }
    // Lấy tất cả hồ sơ người dùng
    public List<User> getAllUserProfiles() {
        return userRepository.findAll();  // Lấy tất cả người dùng
    }

}