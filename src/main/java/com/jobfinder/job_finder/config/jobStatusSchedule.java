package com.jobfinder.job_finder.config;

import com.jobfinder.job_finder.service.JobPostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class jobStatusSchedule {
    @Autowired
    private JobPostingService jobPostingService;

    @Scheduled(fixedRate = 30000)
    public void checkAndStartWork(){
        jobPostingService.startWorkForJob();
    }
    @Scheduled(fixedRate = 30000)
    public void checkAndStopWork(){
        jobPostingService.closeWorkForJob();
    }
    @Scheduled(fixedRate = 30000)
    public void checkAndRejectWork(){
        jobPostingService.autoRejectJobPosting();
    }

}
