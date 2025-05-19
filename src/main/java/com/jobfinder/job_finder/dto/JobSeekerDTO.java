package com.jobfinder.job_finder.dto;

import com.jobfinder.job_finder.util.Role;
import jakarta.persistence.ElementCollection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobSeekerDTO {
    private Long id;
    private String fullName;
    private String email;
    private String profilePicture;
    private String phoneNumber;
    private String birthDate;
    private String workExperience;
    private String education;
    private String skills;
    private String languages;
    private String certifications;
    private String cvFile;

}
