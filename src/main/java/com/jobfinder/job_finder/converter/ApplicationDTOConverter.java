package com.jobfinder.job_finder.converter;

import com.jobfinder.job_finder.dto.ApplicationDTO;
import com.jobfinder.job_finder.entity.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationDTOConverter {
    @Autowired
    private JobSeekerDTOConverter jobSeekerDTOConverter;
    public ApplicationDTO convert(Application application) {
        return new ApplicationDTO(
                application.getId(),
                jobSeekerDTOConverter.toDTO(application.getJobSeeker()),
                application.getJobPosting().getId(),
                application.getApplicationDate(),
                application.getStatus().toString()
        );
    }
}
