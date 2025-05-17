package com.jobfinder.job_finder.converter;

import com.jobfinder.job_finder.dto.response.ShiftDTO;
import com.jobfinder.job_finder.entity.Shift;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShiftConverter {
    @Autowired
    private ModelMapper modelMapper;

    public ShiftDTO convert(Shift shift) {
        ShiftDTO result = modelMapper.map(shift, ShiftDTO.class);
        result.setJobPostingId(shift.getJobPosting().getId());
        return result;
    }
}
