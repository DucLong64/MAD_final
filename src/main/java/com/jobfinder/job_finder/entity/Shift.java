package com.jobfinder.job_finder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // Tên ca làm việc
    private LocalDateTime startTime; // Thời gian bắt đầu
    private LocalDateTime endTime;   // Thời gian kết thúc

    @OneToOne
    @JoinColumn(name="job_posting_id", referencedColumnName = "id")
    private JobPosting jobPosting;

    @OneToMany(mappedBy = "shift")
    private List<ShiftJobSeeker> shiftJobSeekers;  // Mối quan hệ với bảng trung gian

}
