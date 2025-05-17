package com.jobfinder.job_finder.controller;

import com.jobfinder.job_finder.converter.ApplicationDTOConverter;
import com.jobfinder.job_finder.converter.JobSeekerDTOConverter;
import com.jobfinder.job_finder.dto.ApplicationDTO;
import com.jobfinder.job_finder.dto.JobSeekerDTO;
import com.jobfinder.job_finder.dto.response.ApiResponse;
import com.jobfinder.job_finder.entity.Application;
import com.jobfinder.job_finder.entity.JobPosting;
import com.jobfinder.job_finder.entity.JobSeeker;
import com.jobfinder.job_finder.entity.User;
import com.jobfinder.job_finder.service.ApplicationService;
import com.jobfinder.job_finder.service.JobPostingService;
import com.jobfinder.job_finder.service.ShiftService;
import com.jobfinder.job_finder.service.UserService;
import com.jobfinder.job_finder.util.ApplicationStatus;
import com.jobfinder.job_finder.util.JobStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recruiter")
public class RecruiterController {
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationDTOConverter applicationDTOConverter;
    @Autowired
    private JobSeekerDTOConverter jobSeekerDTOConverter;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private ShiftService shiftService;
    @Autowired
    private JobPostingService jobPostingService;

    @GetMapping("/home")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getHomeData(
            @RequestParam Long recruiterId,
            @RequestParam(required = false) Integer month // 1-12
    ) {
        Map<String, Object> data = jobPostingService.getHomeData(recruiterId, month);
        ApiResponse<Map<String, Object>> response = new ApiResponse<>(200, "Success", data);
        return ResponseEntity.ok(response);
    }
    // Lấy thông tin hồ sơ người tìm việc
    @GetMapping("/seeker/{seeker_id}")
    public ResponseEntity<ApiResponse<?>> getSeeker(@PathVariable long seeker_id) {
        try {
            JobSeeker seeker = (JobSeeker) userService.getUserProfile(seeker_id);

            if (seeker == null) {
                // Nếu không tìm thấy người tìm việc, trả về lỗi 404
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(404, "Job seeker not found", null));
            }

            // Chuyển đổi thành DTO và trả về phản hồi thành công
            JobSeekerDTO jobSeekerDTO = jobSeekerDTOConverter.toDTO(seeker);
            return ResponseEntity.ok(new ApiResponse<>(200, "Job seeker profile fetched successfully", jobSeekerDTO));
        } catch (Exception e) {
            // Xử lý ngoại lệ nếu có lỗi xảy ra
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "An error occurred while fetching the job seeker profile: " + e.getMessage(), null));
        }
    }
    // Phê duyệt hoặc từ chối đơn ứng tuyển

    @PutMapping("/update-application-status/{applicationId}")
    public ResponseEntity<ApiResponse<?>> updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestParam ApplicationStatus status) {
        try {
            Application updatedApplication = applicationService.updateApplicationStatus(applicationId, status);

            if (status == ApplicationStatus.ACCEPTED) {
                JobSeeker jobSeeker = updatedApplication.getJobSeeker();  // Lấy ứng viên
                JobPosting jobPosting = updatedApplication.getJobPosting();  // Lấy công việc

                // Tạo hoặc cập nhật Shift cho ứng viên
                shiftService.createOrUpdateShiftForJobSeeker(jobSeeker, jobPosting);
            }
            // Trả về phản hồi thành công
            return ResponseEntity.ok(new ApiResponse<>(200, "Trạng thái đơn ứng tuyển đã được cập nhật: "+updatedApplication.getStatus(), null));
        } catch (Exception e) {
            // Trả về lỗi nếu có sự cố
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "Đã xảy ra lỗi khi cập nhật trạng thái đơn ứng tuyển: " + e.getMessage(), null));
        }

    }
    // Lấy tất cả các đơn ứng tuyển cho một tin tuyển dụng
    @GetMapping("/applications/{jobPostingId}")
    public ResponseEntity<ApiResponse<?>> getApplicationsByJobPosting(@PathVariable Long jobPostingId) {
        try {
            List<Application> applications = applicationService.getApplicationsByJobPosting(jobPostingId);
            List<ApplicationDTO> applicationDTOS = new ArrayList<>();

            for (Application application : applications) {
                ApplicationDTO tmp = applicationDTOConverter.convert(application);
                applicationDTOS.add(tmp);
            }

            // Nếu không có đơn ứng tuyển, trả về mã 204 No Content với thông điệp
            if (applicationDTOS.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(new ApiResponse<>(204, "No applications found for this job posting.", null));
            }

            // Trả về phản hồi thành công với danh sách đơn ứng tuyển
            return ResponseEntity.ok(new ApiResponse<>(200, "Applications fetched successfully", applicationDTOS));

        } catch (Exception e) {
            // Xử lý ngoại lệ nếu có lỗi xảy ra
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "An error occurred while fetching applications: " + e.getMessage(), null));
        }
    }
    @GetMapping("/applications/pending/{jobPostingId}")
    public ResponseEntity<ApiResponse<?>> getApplicationsByJobPostingAndStatus(@PathVariable Long jobPostingId) {
        try {
            List<ApplicationDTO> applicationDTOS = applicationService.getApplicationsByJobPostingIdAndStatus(jobPostingId);
            // Nếu không có đơn ứng tuyển, trả về mã 204 No Content với thông điệp
            if (applicationDTOS.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(new ApiResponse<>(204, "No applications found for this job posting.", null));
            }

            // Trả về phản hồi thành công với danh sách đơn ứng tuyển
            return ResponseEntity.ok(new ApiResponse<>(200, "Applications fetched successfully", applicationDTOS));

        } catch (Exception e) {
            // Xử lý ngoại lệ nếu có lỗi xảy ra
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "An error occurred while fetching applications: " + e.getMessage(), null));
        }
    }


}
