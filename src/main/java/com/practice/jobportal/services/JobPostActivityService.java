package com.practice.jobportal.services;

import com.practice.jobportal.entity.*;
import com.practice.jobportal.repository.JobPostActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<RecruiterJobsDto> getRecruiterJobs(int recruiter){
        List<IRecruiterJobs> recruiterJobs = jobPostActivityRepository.getRecruiterJobs(recruiter);

        List<RecruiterJobsDto> recruiterJobsDtoList = new ArrayList<>();

        for(IRecruiterJobs rec: recruiterJobs){
            JobLocation loc = new JobLocation(rec.getLocationId(),rec.getCity(), rec.getState(), rec.getCountry());
            JobCompany company = new JobCompany(rec.getCompanyId(),rec.getName(), "");
            recruiterJobsDtoList.add(new RecruiterJobsDto(rec.getTotalCandidates(), rec.getJob_post_id(),rec.getJob_title(),
                    loc,company));
        }

        return  recruiterJobsDtoList;

    }

    public JobPostActivity getOne(int id) {
        return  jobPostActivityRepository.findById(id).orElseThrow(()->
                new RuntimeException("Job not found"));
    }
}
