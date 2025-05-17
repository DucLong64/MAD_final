package com.jobfinder.job_finder.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Entity
@DiscriminatorValue("JOB_SEEKER") // Xác định giá trị phân biệt cho người tìm việc
public class JobSeeker extends User {

    private String profilePicture;
    private String birthDate;
    private String workExperience;
    private String education;

    @ElementCollection
    private List<String> skills;

    @ElementCollection
    private List<String> languages;

    @ElementCollection
    private List<String> certifications;

    private String cvFile;  // Đường dẫn đến file CV

    @OneToMany(mappedBy = "jobSeeker")
    @JsonIgnore  // Thêm annotation này để không xuất hiện trong JSON trả về
    private List<ShiftJobSeeker> shiftJobSeekers;  // Mối quan hệ với bảng trung gian job_seeker_shift

}