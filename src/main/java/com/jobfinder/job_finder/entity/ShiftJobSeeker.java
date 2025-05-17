package com.jobfinder.job_finder.entity;

import jakarta.persistence.*;

@Entity
public class ShiftJobSeeker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_seeker_id",referencedColumnName = "id")
    private JobSeeker jobSeeker;  // Khóa ngoại đến JobSeeker

    @ManyToOne
    @JoinColumn(name = "shift_id",referencedColumnName = "id")
    private Shift shift;  // Khóa ngoại đến Shift

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JobSeeker getJobSeeker() {
        return jobSeeker;
    }

    public void setJobSeeker(JobSeeker jobSeeker) {
        this.jobSeeker = jobSeeker;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }
}
