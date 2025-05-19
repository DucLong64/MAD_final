package com.jobfinder.job_finder.service;

import com.jobfinder.job_finder.converter.JobPostingDTOConverter;
import com.jobfinder.job_finder.dto.JobPostingDTO;
import com.jobfinder.job_finder.entity.JobPosting;
import com.jobfinder.job_finder.repository.JobPostingRepository;
import com.jobfinder.job_finder.util.JobStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeekerService {
    @Autowired
    private JobPostingRepository jobPostingRepository;
    @Autowired
    private JobPostingDTOConverter jobPostingDTOConverter;
    public List<JobPostingDTO> searchForSeeker(String searchText) {
        List<JobPosting> jobList = jobPostingRepository.findByTitleContainingIgnoreCaseAndStatus(searchText, JobStatus.OPEN);
        List<JobPostingDTO> jobPostingDTOList = new ArrayList<>();
        for (JobPosting jobPosting : jobList) {
            JobPostingDTO jobPostingDTO = jobPostingDTOConverter.toJobPostingDTO(jobPosting);
            jobPostingDTOList.add(jobPostingDTO);
        }
        return jobPostingDTOList;
    }
}
