package com.jobfinder.job_finder.dto.request;

public class ApproveRequestDTO {
    private Long applicationId;
    private String status;

    public Long getApplicationId() {
        return applicationId;
    }

    public String getStatus() {
        return status;
    }
}
