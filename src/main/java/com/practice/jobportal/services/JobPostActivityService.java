package com.practice.jobportal.services;

import com.practice.jobportal.entity.JobPostActivity;
import com.practice.jobportal.repository.JobPostActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobPostActivityService {

    private final JobPostActivityRepository jobPostActivityRepository;

    @Autowired
    public JobPostActivityService (JobPostActivityRepository jobPostActivityRepository){
      this.jobPostActivityRepository= jobPostActivityRepository;
    }

    public JobPostActivity addNew(JobPostActivity jobPostActivity){
        return jobPostActivityRepository.save(jobPostActivity);
    }
}