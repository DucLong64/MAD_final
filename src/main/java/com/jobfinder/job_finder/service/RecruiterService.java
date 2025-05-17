package com.jobfinder.job_finder.service;

import com.jobfinder.job_finder.entity.Recruiter;
import com.jobfinder.job_finder.repository.JobPostingRepository;
import com.jobfinder.job_finder.repository.RecruiterRepository;
import com.jobfinder.job_finder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecruiterService {

    @Autowired
    private RecruiterRepository recruiterRepository;

    // Lấy thông tin nhà tuyển dụng theo ID
    public Recruiter getRecruiterById(Long recruiterId) {
        return recruiterRepository.findById(recruiterId)
                .orElseThrow(() -> new RuntimeException("Recruiter not found"));
    }


}
