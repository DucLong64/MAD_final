package com.jobfinder.job_finder.dto;

import com.jobfinder.job_finder.entity.JobSeeker;

import java.time.LocalDateTime;

public class ApplicationDTO {
    private Long idApplication;
    private JobSeekerDTO jobSeeker;
    private Long idJobPosting;
    private LocalDateTime applicationDate;
    private String status;

    // Constructor
    public ApplicationDTO(Long idApplication, JobSeekerDTO jobSeeker, Long idJobPosting, LocalDateTime applicationDate, String status) {
        this.idApplication = idApplication;
        this.jobSeeker = jobSeeker;
        this.idJobPosting = idJobPosting;
        this.applicationDate = applicationDate;
        this.status = status;
    }

    public Long getIdApplication() {
        return idApplication;
    }

    public void setIdApplication(Long idApplication) {
        this.idApplication = idApplication;
    }

    public JobSeekerDTO getJobSeeker() {
        return jobSeeker;
    }

    public void setJobSeeker(JobSeekerDTO jobSeeker) {
        this.jobSeeker = jobSeeker;
    }

    public Long getIdJobPosting() {
        return idJobPosting;
    }

    public void setIdJobPosting(Long idJobPosting) {
        this.idJobPosting = idJobPosting;
    }

    public LocalDateTime getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDateTime applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
