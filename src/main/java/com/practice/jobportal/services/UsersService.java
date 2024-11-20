package com.practice.jobportal.services;

import com.practice.jobportal.entity.JobSeekerProfile;
import com.practice.jobportal.entity.RecruiterProfile;
import com.practice.jobportal.entity.Users;
import com.practice.jobportal.repository.JobSeekerProfileRepository;
import com.practice.jobportal.repository.RecruiterProfileRepository;
import com.practice.jobportal.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersService(UsersRepository usersRepository, RecruiterProfileRepository recruiterProfileRepository
    , JobSeekerProfileRepository jobSeekerProfileRepository,PasswordEncoder passwordEncoder){
        this.usersRepository=usersRepository;
        this.recruiterProfileRepository=recruiterProfileRepository;
        this.jobSeekerProfileRepository=jobSeekerProfileRepository;
        this.passwordEncoder=passwordEncoder;
    }

    public Users addNew(Users users){
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        users.setPassword(passwordEncoder.encode(users.getPassword()));

        int userTypeId= users.getUserType().getUserTypeId();
       Users saveUsers = usersRepository.save(users);

       if(userTypeId == 1){
           recruiterProfileRepository.save(new RecruiterProfile(saveUsers));
       }else{
           jobSeekerProfileRepository.save(new JobSeekerProfile(saveUsers));
       }

        return saveUsers;
    }

    //TODO OJO: este metodo carga el perfil del usuario logeado, se valida su autorizacion y se procede a buscar su perfil el cual es tretornado como resultado
    public Object getCurrentUserProfile(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username = authentication.getName();
            Users users = usersRepository.findByEmail(username).orElseThrow(()->
            new UsernameNotFoundException("Could not found " + "user"));
            int userId= users.getUserId();
            if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))){
                return  recruiterProfileRepository.findById(userId).orElse(new RecruiterProfile());
            }else{
                return jobSeekerProfileRepository.findById(userId).orElse( new JobSeekerProfile());
            }
        }
        return null;

    }

    public Optional<Users> getUserByEmail(String email){
        return usersRepository.findByEmail(email);
    }
}
