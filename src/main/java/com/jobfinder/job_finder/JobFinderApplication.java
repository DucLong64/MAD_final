package com.jobfinder.job_finder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JobFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobFinderApplication.class, args);
	}

}
