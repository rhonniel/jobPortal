package com.practice.jobportal.services;

import com.practice.jobportal.entity.JobSeekerProfile;
import com.practice.jobportal.entity.RecruiterProfile;
import com.practice.jobportal.entity.Users;
import com.practice.jobportal.repository.JobSeekerProfileRepository;
import com.practice.jobportal.repository.RecruiterProfileRepository;
import com.practice.jobportal.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository, RecruiterProfileRepository recruiterProfileRepository
    , JobSeekerProfileRepository jobSeekerProfileRepository){
        this.usersRepository=usersRepository;
        this.recruiterProfileRepository=recruiterProfileRepository;
        this.jobSeekerProfileRepository=jobSeekerProfileRepository;
    }

    public Users addNew(Users users){
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
       int userTypeId= users.getUserType().getUserTypeId();
       Users saveUsers = usersRepository.save(users);

       if(userTypeId == 1){
           recruiterProfileRepository.save(new RecruiterProfile(saveUsers));
       }else{
           jobSeekerProfileRepository.save(new JobSeekerProfile(saveUsers));
       }

        return saveUsers;
    }

    public Optional<Users> getUserByEmail(String email){
        return usersRepository.findByEmail(email);
    }
}
