package com.jobfinder.job_finder.entity;

import com.jobfinder.job_finder.util.ApplicationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="job_seeker_id", referencedColumnName = "id")
    private JobSeeker jobSeeker;

    @ManyToOne
    @JoinColumn(name="job_posting_id", referencedColumnName = "id")
    private JobPosting jobPosting;
    private LocalDateTime applicationDate;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
}
