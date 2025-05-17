package com.jobfinder.job_finder.repository;

import com.jobfinder.job_finder.entity.Application;
import com.jobfinder.job_finder.entity.JobPosting;
import com.jobfinder.job_finder.entity.JobSeeker;
import com.jobfinder.job_finder.util.ApplicationStatus;
import com.jobfinder.job_finder.util.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Optional<Application> findByJobSeekerAndJobPosting (JobSeeker jobSeeker, JobPosting jobPosting);
    // Bạn có thể thêm các phương thức tùy chỉnh nếu cần
    List<Application> findByJobPostingId(Long jobPostingId);
    List<Application> findByJobPostingIdAndStatus(Long jobId, ApplicationStatus status);
}