package com.jobfinder.job_finder.repository;

import com.jobfinder.job_finder.entity.ShiftJobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShiftJobSeekerRepository extends JpaRepository<ShiftJobSeeker, Long> {
    List<ShiftJobSeeker> findByJobSeekerId(Long id);
}
