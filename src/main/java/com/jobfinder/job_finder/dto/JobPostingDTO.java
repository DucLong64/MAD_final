package com.jobfinder.job_finder.dto;

import com.jobfinder.job_finder.util.JobStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobPostingDTO {
    private Long id;
    private String title;
    private String description;
    private String requirement;
    private String salary;
    private String benefit;
    private String location;
    private Long numberOfPositions;
    private LocalDateTime postDate;
    private LocalDateTime deadLine;
    private LocalDateTime updatedDate;
    @Enumerated(EnumType.STRING)
    private JobStatus status;
    private String recruiter;  // DTO cho recruiter
    private String shift;

}