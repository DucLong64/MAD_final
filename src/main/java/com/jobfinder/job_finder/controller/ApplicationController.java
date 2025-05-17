package com.jobfinder.job_finder.controller;

import com.jobfinder.job_finder.dto.ApplicationDTO;
import com.jobfinder.job_finder.dto.response.ApiResponse;
import com.jobfinder.job_finder.entity.Application;
import com.jobfinder.job_finder.entity.JobPosting;
import com.jobfinder.job_finder.entity.JobSeeker;
import com.jobfinder.job_finder.repository.ApplicationRepository;
import com.jobfinder.job_finder.service.ApplicationService;
import com.jobfinder.job_finder.service.JobPostingService;
import com.jobfinder.job_finder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private UserService userService;
    @Autowired
    private JobPostingService jobPostingService;
    @Autowired
    private ApplicationRepository applicationRepository;

    @PostMapping("/apply")
    public ApiResponse<?> applyForJob(@RequestParam Long jobSeekerId,
                                      @RequestParam Long jobPostingId) {
        JobSeeker jobSeeker = (JobSeeker) userService.getUserProfile(jobSeekerId);
        JobPosting jobPosting = jobPostingService.getJobPostingById(jobPostingId);

        if (jobSeeker == null || jobPosting == null) {
            return new ApiResponse<>(400, "Candidate or job not exist!", null);
        }

        try {
            applicationService.applyForJob(jobSeeker, jobPosting);
            return new ApiResponse<>(200, "Application submitted successfully!", null);

        } catch (IllegalStateException e) {
            return new ApiResponse<>(400, "You have submitted yet!", null);
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<ApplicationDTO>> getAllApplications() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }
}