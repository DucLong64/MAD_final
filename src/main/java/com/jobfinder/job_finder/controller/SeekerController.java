package com.jobfinder.job_finder.controller;

import com.jobfinder.job_finder.dto.JobPostingDTO;
import com.jobfinder.job_finder.dto.response.ShiftDTO;
import com.jobfinder.job_finder.service.JobPostingService;
import com.jobfinder.job_finder.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seeker")
public class SeekerController {
    @Autowired
    private ShiftService shiftService;
    @Autowired
    private JobPostingService jobPostingService;
    @GetMapping("/schedule/{seekerId}")
    public ResponseEntity<List<ShiftDTO>> getSchedule(@PathVariable Long seekerId) {
        return ResponseEntity.ok(shiftService.getScheduleForSeeker(seekerId));
    }
    @GetMapping("/jobs/all")
    public ResponseEntity<List<JobPostingDTO>> getJobPostings() {
        List<JobPostingDTO> jobPostings = jobPostingService.getAllOpenJobPostings();
        return ResponseEntity.ok(jobPostings);
    }

}
