package com.jobfinder.job_finder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}
