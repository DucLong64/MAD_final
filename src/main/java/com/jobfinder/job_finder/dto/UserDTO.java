package com.jobfinder.job_finder.dto;

import com.jobfinder.job_finder.util.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String fullName;
    private String email;
    private String password;
    private Role role;

    // Thông tin của người tìm việc
    private String profilePicture;
    private String phoneNumber;
    private String birthDate;
    private String workExperience;
    private String education;
    private List<String> skills;
    private List<String> languages;
    private List<String> certifications;
    private String cvFile;  // Đường dẫn đến file CV

    // Thông tin của nhà tuyển dụng
    private String companyName;
    private String companyAddress;
    private String companyPhoneNumber;
    private String companyLogo;

}