package com.jobfinder.job_finder.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public JobPosting getJobPosting() {
        return jobPosting;
    }

    public void setJobPosting(JobPosting jobPosting) {
        this.jobPosting = jobPosting;
    }

    public List<ShiftJobSeeker> getShiftJobSeekers() {
        return shiftJobSeekers;
    }

    public void setShiftJobSeekers(List<ShiftJobSeeker> shiftJobSeekers) {
        this.shiftJobSeekers = shiftJobSeekers;
    }
}
