package com.jobfinder.job_finder.controller;


import com.jobfinder.job_finder.converter.JobPostingDTOConverter;
import com.jobfinder.job_finder.dto.JobPostingDTO;
import com.jobfinder.job_finder.dto.response.ApiResponse;
import com.jobfinder.job_finder.entity.JobPosting;
import com.jobfinder.job_finder.entity.Recruiter;
import com.jobfinder.job_finder.entity.User;
import com.jobfinder.job_finder.service.JobPostingService;
import com.jobfinder.job_finder.service.RecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recruiter")
public class JobPostingController {
    @Autowired
    private JobPostingService jobPostingService;
    @Autowired
    private RecruiterService recruiterService;
    @Autowired
    private JobPostingDTOConverter jobPostingDTOConverter;
    // Đăng tin tuyển dụng
    @PostMapping("/post-job")
    public ResponseEntity<ApiResponse<?>> postJob(@RequestBody JobPosting jobPosting, @RequestParam Long recruiterId) {
        try {
            // Kiểm tra xem deadline có phải là quá khứ không
            if (jobPosting.getDeadLine().isBefore(LocalDateTime.now())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(400, "The deadline cannot be in the past.", null));
            }

            // Tìm kiếm nhà tuyển dụng
            Recruiter recruiter = recruiterService.getRecruiterById(recruiterId);
            if (recruiter == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(404, "Recruiter not found", null));
            }
            jobPosting.setRecruiter(recruiter);
            // Tạo job tuyển dụng mới
            JobPostingDTO createdJob = jobPostingService.createJobPosting(jobPosting);

            // Trả về phản hồi thành công
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(201, "Job posted successfully", createdJob));
        } catch (Exception e) {
            // Xử lý ngoại lệ nếu có lỗi xảy ra
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "An error occurred while posting the job: " + e.getMessage(), null));
        }
    }
    // Lấy danh sách tin tuyển dụng của nhà tuyển dụng
    @GetMapping("/jobs/{recuiterId}")
    public ResponseEntity<ApiResponse<?>> getAllJobPostings(@PathVariable Long recuiterId) {
        try {
            List<JobPostingDTO> jobs = jobPostingService.getJobPostings(recuiterId);

            if (jobs == null || jobs.isEmpty()) {
                // Nếu không tìm thấy tin tuyển dụng, trả về lỗi 404
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(404, "No job postings found for this recruiter.", null));
            }

            // Trả về phản hồi thành công
            return ResponseEntity.ok(new ApiResponse<>(200, "Job postings fetched successfully", jobs));
        } catch (Exception e) {
            // Xử lý ngoại lệ nếu có lỗi xảy ra
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "An error occurred while fetching job postings: " + e.getMessage(), null));
        }
    }

    // Cập nhật tin tuyển dụng
    @PutMapping("/update-job/{jobId}")
    public ResponseEntity<ApiResponse<?>> updateJob(@PathVariable Long jobId, @RequestBody JobPosting jobPosting) {
        try {
            // Kiểm tra xem deadline có phải là quá khứ không
            if (jobPosting.getDeadLine().isBefore(LocalDateTime.now())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(400, "The deadline cannot be in the past.", null));
            }
            // Tìm kiếm tin tuyển dụng cần cập nhật
            JobPostingDTO existingJob = jobPostingService.updateJobPosting(jobId, jobPosting);
            if (existingJob == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(404, "Job not found", null));
            }

            // Tạo phản hồi thành công
            return ResponseEntity.ok(new ApiResponse<>(200, "Job updated successfully", existingJob));
        } catch (Exception e) {
            // Xử lý ngoại lệ nếu có lỗi xảy ra
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "An error occurred while updating the job: " + e.getMessage(), null));
        }
    }


    @DeleteMapping("/delete-job/{jobId}")
    public ResponseEntity<ApiResponse<?>> deleteJob(@PathVariable Long jobId) {
        try {
            jobPostingService.deleteJobPosting(jobId);
            // Trả về phản hồi thành công
            return ResponseEntity.ok(new ApiResponse<>(200, "Job posting deleted successfully", null));
        } catch (Exception e) {
            // Trả về lỗi nếu không tìm thấy tin tuyển dụng hoặc có lỗi khác
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "Job posting not found: " + e.getMessage(), null));
        }
    }
    // Lấy tin tuyển dụng theo ID
    @GetMapping("/job/{jobId}")
    public ResponseEntity<ApiResponse<?>> getJobPosting(@PathVariable Long jobId) {
        try {
            JobPosting jobPosting = jobPostingService.getJobPostingById(jobId);

            if (jobPosting == null) {
                // Nếu không tìm thấy tin tuyển dụng, trả về lỗi 404
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(404, "Job posting not found", null));
            }

            // Trả về phản hồi thành công
            return ResponseEntity.ok(new ApiResponse<>(200, "Job posting fetched successfully", jobPostingDTOConverter.toJobPostingDTO(jobPosting)));
        } catch (Exception e) {
            // Xử lý ngoại lệ nếu có lỗi xảy ra
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "An error occurred while fetching the job posting: " + e.getMessage(), null));
        }
    }
    // Lay tat ca cac tin
    // Lấy tất cả các tin tuyển dụng
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
}
