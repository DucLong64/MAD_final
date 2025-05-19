package com.jobfinder.job_finder.repository;

import com.jobfinder.job_finder.entity.JobPosting;
import com.jobfinder.job_finder.util.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
    List<JobPosting> findByRecruiterId(Long recruiterId);  // Tìm các tin tuyển dụng của nhà tuyển dụng
    Optional<JobPosting> findByIdAndRecruiterId(Long jobId, Long recruiterId);  // Tìm tin tuyển dụng theo id và nhà tuyển dụng
    Optional<JobPosting> findById(Long jobId);
    List<JobPosting> findByStatus(JobStatus status);

    @Query("SELECT j FROM JobPosting j WHERE j.recruiter.id = :recruiterId AND FUNCTION('MONTH', j.postDate) = :month")
    List<JobPosting> findByRecruiterIdAndMonth(@Param("recruiterId") Long recruiterId, @Param("month") Integer month);
    // Mới: tìm theo recruiterId, năm và tháng
    @Query("SELECT j FROM JobPosting j WHERE j.recruiter.id = :recruiterId AND FUNCTION('YEAR', j.postDate) = :year AND FUNCTION('MONTH', j.postDate) = :month")
    List<JobPosting> findByRecruiterIdAndYearAndMonth(@Param("recruiterId") Long recruiterId, @Param("year") Integer year, @Param("month") Integer month);

    List<JobPosting> findByTitleContainingIgnoreCaseAndStatus(String title, JobStatus status);
}