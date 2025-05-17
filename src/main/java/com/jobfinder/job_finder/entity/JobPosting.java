package com.jobfinder.job_finder.entity;

import com.jobfinder.job_finder.util.JobStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class JobPosting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String requirement;
    private String salary;
    private String benefit;
    private String location;
    private Long numberOfPositions;
    private LocalDateTime postDate;
    private LocalDateTime updatedDate;
    private LocalDateTime deadLine;
    @Enumerated(EnumType.STRING)
    private JobStatus status;

    @ManyToOne
    @JoinColumn(name = "recruiter_id", referencedColumnName = "id")
    private User recruiter;

    @OneToOne(mappedBy = "jobPosting", cascade = CascadeType.ALL)
    private Shift shift;

    @OneToMany(mappedBy = "jobPosting", cascade = CascadeType.ALL)
    private List<Application> applications;

}