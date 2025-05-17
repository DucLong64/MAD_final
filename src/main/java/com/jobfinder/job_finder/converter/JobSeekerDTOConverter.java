package com.jobfinder.job_finder.converter;

import com.jobfinder.job_finder.dto.JobPostingDTO;
import com.jobfinder.job_finder.dto.JobSeekerDTO;
import com.jobfinder.job_finder.entity.JobSeeker;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobSeekerDTOConverter {
    @Autowired
    private ModelMapper modelMapper;
    public JobSeekerDTO toDTO(JobSeeker jobSeeker) {
        JobSeekerDTO result = modelMapper.map(jobSeeker, JobSeekerDTO.class);
        List<String> skills = jobSeeker.getSkills();
        List<String>languages =jobSeeker.getLanguages();
        List<String>certifications= jobSeeker.getCertifications();
        String skillResult = String.join(", ", skills);
        String languageResult = String.join(", ", languages);
        String certificationResult = String.join(", ", certifications);
        result.setSkills(skillResult);
        result.setLanguages(languageResult);
        result.setCertifications(certificationResult);
        return result;
    }


}
