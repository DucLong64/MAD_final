package com.jobfinder.job_finder.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShiftDTO {
    private Long id;
    private String name;
    private String startTime;
    private String endTime;

    private Long jobPostingId;
}

