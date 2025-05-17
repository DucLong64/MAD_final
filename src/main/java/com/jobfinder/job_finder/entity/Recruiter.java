package com.jobfinder.job_finder.entity;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("RECRUITER")
public class Recruiter extends User{
    private String companyName;
    private String companyAddress;
    private String companyPhoneNumber;
    private String companyLogo;

}
