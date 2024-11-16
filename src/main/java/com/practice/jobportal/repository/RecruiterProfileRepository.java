package com.practice.jobportal.repository;

import com.practice.jobportal.entity.RecruiterProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruiterProfileRepository  extends JpaRepository<RecruiterProfile,Integer> {
}
