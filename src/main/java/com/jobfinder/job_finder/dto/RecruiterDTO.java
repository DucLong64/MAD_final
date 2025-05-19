package com.jobfinder.job_finder.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecruiterDTO {
    private String fullName;
    private String email;
    private String companyName;

}
