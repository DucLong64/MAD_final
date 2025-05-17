package com.jobfinder.job_finder.service;

import com.jobfinder.job_finder.converter.ShiftConverter;
import com.jobfinder.job_finder.dto.response.ShiftDTO;
import com.jobfinder.job_finder.entity.JobPosting;
import com.jobfinder.job_finder.entity.JobSeeker;
import com.jobfinder.job_finder.entity.Shift;
import com.jobfinder.job_finder.entity.ShiftJobSeeker;
import com.jobfinder.job_finder.repository.ShiftJobSeekerRepository;
import com.jobfinder.job_finder.repository.ShiftRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShiftService {
    @Autowired
    private ShiftRepository shiftRepository;
    @Autowired
    private ShiftConverter shiftConverter;
    @Autowired
    private ShiftJobSeekerRepository shiftJobSeekerRepository;
    @Transactional
    public void deleteAllShiftsByJobId(Long jobId) {
        shiftRepository.deleteByJobPostingId(jobId);
    }

    public List<ShiftDTO> getScheduleForSeeker(Long seekerId) {
        List<Shift> shifts = shiftRepository.findByJobSeekerId(seekerId);
        List<ShiftDTO> shiftDTOs = new ArrayList<>();
        for (Shift shift : shifts) {
            ShiftDTO shiftDTO = shiftConverter.convert(shift);
            shiftDTOs.add(shiftDTO);
        }
        return shiftDTOs;
    }
    public void createOrUpdateShiftForJobSeeker(JobSeeker jobSeeker, JobPosting jobPosting) {
        //Lấy ca làm việc
        Shift jobPostingShift = jobPosting.getShift();
        if (jobPostingShift != null) {
            // Kiểm tra xem ứng viên đã có ca làm cho công việc này chưa
            List<ShiftJobSeeker> existingShifts = shiftJobSeekerRepository.findByJobSeekerId(jobSeeker.getId());

            boolean hasShift = existingShifts.stream()
                    .anyMatch(shiftJobSeeker -> shiftJobSeeker.getShift().equals(jobPostingShift));

            if (!hasShift) {
                // Nếu chưa có ca làm, tạo mới ShiftJobSeeker
                ShiftJobSeeker shiftJobSeeker = new ShiftJobSeeker();
                shiftJobSeeker.setJobSeeker(jobSeeker);
                shiftJobSeeker.setShift(jobPostingShift);
                shiftJobSeekerRepository.save(shiftJobSeeker);  // Lưu vào cơ sở dữ liệu
            }
        }
    }


}
