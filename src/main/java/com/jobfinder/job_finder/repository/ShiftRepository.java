package com.jobfinder.job_finder.repository;

import com.jobfinder.job_finder.entity.JobPosting;
import com.jobfinder.job_finder.entity.JobSeeker;
import com.jobfinder.job_finder.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShiftRepository extends JpaRepository<Shift, Long> {
    void deleteByJobPostingId(Long id);

    // Tìm kiếm các ca làm cho một ứng viên thông qua bảng trung gian
    @Query("SELECT s FROM Shift s JOIN ShiftJobSeeker sj ON s.id = sj.shift.id WHERE sj.jobSeeker.id = :jobSeekerId")
    List<Shift> findByJobSeekerId(@Param("jobSeekerId") Long jobSeekerId);
}
