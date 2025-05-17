package com.jobfinder.job_finder.service;

import com.jobfinder.job_finder.converter.JobPostingDTOConverter;
import com.jobfinder.job_finder.dto.JobPostingDTO;
import com.jobfinder.job_finder.entity.JobPosting;
import com.jobfinder.job_finder.entity.Shift;
import com.jobfinder.job_finder.repository.JobPostingRepository;
import com.jobfinder.job_finder.repository.ShiftRepository;
import com.jobfinder.job_finder.util.JobStatus;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JobPostingService {
    @Autowired
    private JobPostingRepository jobPostingRepository;
    @Autowired
    private ShiftRepository shiftRepository;
    @Autowired
    private JobPostingDTOConverter jobPostingDTOConverter;
    @Autowired
    private ShiftService shiftService;
    @Autowired
    private ModelMapper modelMapper;

    public JobPostingDTO createJobPosting(JobPosting jobPosting) {
        Shift shift = jobPosting.getShift();
        jobPosting.setPostDate(java.time.LocalDateTime.now());
        jobPosting.setUpdatedDate(java.time.LocalDateTime.now());
        jobPosting.setStatus(JobStatus.PENDING);
        JobPosting savedJobPosting = jobPostingRepository.save(jobPosting);
        if (shift != null) {
            shift.setJobPosting(savedJobPosting);
            shiftRepository.save(shift);
        }
        return jobPostingDTOConverter.toJobPostingDTO(savedJobPosting);
    }
    // Tìm kiếm tin tuyển dụng của nhà tuyển dụng
    public List<JobPostingDTO> getJobPostings(Long recruiterId) {
        List<JobPosting> jobPostings = jobPostingRepository.findByRecruiterId(recruiterId);
        List<JobPostingDTO> jobPostingDTOS= new ArrayList<>();
        for(JobPosting jobPosting : jobPostings) {
            JobPostingDTO jobDTO = jobPostingDTOConverter.toJobPostingDTO(jobPosting);
            jobPostingDTOS.add(jobDTO);
        }
        return jobPostingDTOS;
    }
    // Cập nhật tin tuyển dụng
    public JobPostingDTO updateJobPosting(Long jobId, JobPosting jobPosting) {
        Optional<JobPosting> existingJob = jobPostingRepository.findById(jobId);
        if (existingJob.isPresent()) {
            JobPosting updatedJob = existingJob.get();
            updatedJob.setTitle(jobPosting.getTitle());
            updatedJob.setDescription(jobPosting.getDescription());
            updatedJob.setBenefit(jobPosting.getBenefit());
            updatedJob.setLocation(jobPosting.getLocation());
            updatedJob.setRequirement(jobPosting.getRequirement());
            updatedJob.setSalary(jobPosting.getSalary());
            updatedJob.setNumberOfPositions(jobPosting.getNumberOfPositions());
            updatedJob.setDeadLine(jobPosting.getDeadLine());
            updatedJob.setUpdatedDate(java.time.LocalDateTime.now());

            // Lấy ca làm duy nhất
            Shift shift = jobPosting.getShift();

            // Xóa ca làm cũ liên quan đến tin tuyển dụng này (nếu có) và thêm ca làm việc mới
            if (shift != null) {
                // Kiểm tra xem ca làm có thay đổi không
                Shift existingShift = updatedJob.getShift(); // Lấy ca làm hiện tại
                if (existingShift != null) {
                    // Nếu ca làm có thay đổi, cập nhật các thuộc tính
                    existingShift.setName(shift.getName()); // Cập nhật tên ca
                    existingShift.setStartTime(shift.getStartTime()); // Cập nhật thời gian bắt đầu
                    existingShift.setEndTime(shift.getEndTime()); // Cập nhật thời gian kết thúc

                    // Lưu ca làm đã cập nhật
                    shiftRepository.save(existingShift);
                } else {
                    // Nếu không có ca làm cũ, thêm ca làm mới
                    shift.setJobPosting(updatedJob); // Liên kết ca làm với bài đăng tuyển
                    shiftRepository.save(shift); // Lưu ca làm mới
                }
            }
            // Lưu lại thông tin tin tuyển dụng đã cập nhật
            jobPostingRepository.save(updatedJob);
            return jobPostingDTOConverter.toJobPostingDTO(updatedJob);
        } else {
            throw new RuntimeException("Job posting not found or does not belong to this recruiter");
        }
    }
    // Hủy tin tuyển dụng
    public void deleteJobPosting(Long jobId) {
        jobPostingRepository.deleteById(jobId);

    }

    // Thay đổi trạng thái tin tuyển dụng
    public void updateStatusJobPosting(Long jobId, JobStatus jobStatus) {
        Optional<JobPosting> existingJob = jobPostingRepository.findById(jobId);
        if (existingJob.isPresent()) {
            JobPosting jobPosting = existingJob.get();
            jobPosting.setStatus(jobStatus);
            jobPostingRepository.save(jobPosting);
        }
    }
    // Lấy tất cả các tin tuyển dụng của tất cả nhà tuyển dụng
    public List<JobPostingDTO> getAllJobPostings() {
        List<JobPosting> jobPostings = jobPostingRepository.findAll();
        List<JobPostingDTO> jobPostingDTOS= new ArrayList<>();
        for(JobPosting jobPosting : jobPostings) {
            JobPostingDTO jobDTO = jobPostingDTOConverter.toJobPostingDTO(jobPosting);
            jobPostingDTOS.add(jobDTO);
        }
        return jobPostingDTOS;
    }
    // Lấy tất cả các tin tuyển dụng có trạng thái Open
    public List<JobPostingDTO> getAllOpenJobPostings() {
        List<JobPosting> jobPostings = jobPostingRepository.findByStatus(JobStatus.OPEN);
        List<JobPostingDTO> jobPostingDTOS= new ArrayList<>();
        for(JobPosting jobPosting : jobPostings) {
            JobPostingDTO jobDTO = jobPostingDTOConverter.toJobPostingDTO(jobPosting);
            jobPostingDTOS.add(jobDTO);
        }
        return jobPostingDTOS;
    }

    public JobPosting getJobPostingById(Long jobPostingId) {
        return jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new EntityNotFoundException("JobPosting not found with id: " + jobPostingId));
    }
    public Map<String, Object> getHomeData(Long recruiterId, Integer month) {
        List<JobPosting> jobPostings;

        if (month != null) {
            jobPostings = jobPostingRepository.findByRecruiterIdAndMonth(recruiterId, month);
        } else {
            jobPostings = jobPostingRepository.findByRecruiterId(recruiterId);
        }

        int open = 0, working = 0, closed = 0;
        List<Map<String, Object>> jobs = new ArrayList<>();
        for (JobPosting job : jobPostings) {
            switch (job.getStatus().toString()) {
                case "OPEN": open++;
                    Map<String, Object> jobInfo = new HashMap<>();
                    jobInfo.put("title", job.getTitle());
                    jobInfo.put("createAt", job.getPostDate());
                    jobInfo.put("updateAt", job.getUpdatedDate());
                    jobInfo.put("endAt", job.getDeadLine());
                    jobInfo.put("numberOfRecruit", job.getNumberOfPositions());
                    jobInfo.put("numberOfApplicants", job.getApplications().size());
                    jobInfo.put("companyAddress", job.getLocation());
                    jobs.add(jobInfo);
                    break;
                case "WORKING": working++; break;
                case "CLOSE": closed++; break;
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("OPEN", open);
        result.put("WORKING", working);
        result.put("CLOSE", closed);
        result.put("job", jobs);

        return result;
    }

}
