package com.jobfinder.job_finder.dto;

import com.jobfinder.job_finder.entity.JobSeeker;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDTO {
    private Long idApplication;
    private JobSeekerDTO jobSeeker;
    private Long idJobPosting;
    private LocalDateTime applicationDate;
    private String status;

}
