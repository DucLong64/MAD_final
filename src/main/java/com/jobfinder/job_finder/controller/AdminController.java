package com.jobfinder.job_finder.controller;

import com.jobfinder.job_finder.dto.JobPostingDTO;
import com.jobfinder.job_finder.dto.response.ApiResponse;
import com.jobfinder.job_finder.service.JobPostingService;
import com.jobfinder.job_finder.util.JobStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/")
public class AdminController {
    @Autowired
    private JobPostingService jobPostingService;

    // Lay tat ca cac tin
    @GetMapping("/jobs/all")
    public ResponseEntity<ApiResponse<?>> getJobPostings() {
        try {
            List<JobPostingDTO> jobPostings = jobPostingService.getAllJobPostings();

            if (jobPostings == null || jobPostings.isEmpty()) {
                // Nếu không có tin tuyển dụng, trả về lỗi 404
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(404, "No job postings found", null));
            }

            // Trả về phản hồi thành công
            return ResponseEntity.ok(new ApiResponse<>(200, "All job postings fetched successfully", jobPostings));
        } catch (Exception e) {
            // Xử lý ngoại lệ nếu có lỗi xảy ra
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "An error occurred while fetching job postings: " + e.getMessage(), null));
        }
    }

    @PutMapping("/jobs/approve/{jobId}")
    public ResponseEntity<ApiResponse<?>> updateStatusJobPosting(
            @PathVariable Long jobId,
            @RequestParam JobStatus jobStatus) {
        try {
            // Cập nhật trạng thái công việc
            jobPostingService.updateStatusJobPosting(jobId, jobStatus);

            // Trả về phản hồi thành công
            return ResponseEntity.ok(new ApiResponse<>(200, "Job status updated successfully: "+ jobStatus, null));
        } catch (Exception e) {
            // Trả về lỗi nếu có sự cố
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "An error occurred while updating the job status: " + e.getMessage(), null));
        }
    }
}
