package com.practice.jobportal.repository;

import com.practice.jobportal.entity.JobSeekerProfile;
import com.practice.jobportal.entity.RecruiterProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobSeekerProfileRepository extends JpaRepository<JobSeekerProfile,Integer> {
}
