package com.practice.jobportal.controller;

import com.practice.jobportal.entity.JobPostActivity;
import com.practice.jobportal.entity.RecruiterJobsDto;
import com.practice.jobportal.entity.RecruiterProfile;
import com.practice.jobportal.entity.Users;
import com.practice.jobportal.services.JobPostActivityService;
import com.practice.jobportal.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;

@Controller
public class JobPostActivityController {

    private final UsersService usersService;
    private final JobPostActivityService jobPostActivityService;

    @Autowired
    public JobPostActivityController (UsersService usersService ,JobPostActivityService jobPostActivityService){
        this.usersService = usersService;
        this.jobPostActivityService = jobPostActivityService;
    }

    @GetMapping("/dashboard/")
    public String searchJobs(Model model){

        Object currentUserProfile = usersService.getCurrentUserProfile();
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        // TODO OJO: yo creo que  el preguinta si el usario esta autenticado no entra en la pregunta sino entonces si no le llego mucho o es alrevez
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUserName = authentication.getName();
            model.addAttribute("username", currentUserName);
            if(authentication.getAuthorities().contains( new SimpleGrantedAuthority("Recruiter"))){
               List<RecruiterJobsDto> recruiterJobs= jobPostActivityService
                       .getRecruiterJobs(((RecruiterProfile)currentUserProfile).getUserAccountId()); // TODO OJO: se ve raro pero solo esta casteando el objeto y luego sacando la propiedad
                model.addAttribute("jobPost", recruiterJobs);
            }



        }
        model.addAttribute("user",currentUserProfile);

        return "dashboard";

    }

    @GetMapping("/dashboard/add")
    public String addJobs(Model model){
        model.addAttribute("jobPostActivity", new JobPostActivity());
        model.addAttribute("user",usersService.getCurrentUserProfile());
        return "add-jobs";
    }

    @PostMapping("/dashboard/addNew")
    public  String addNew(JobPostActivity jobPostActivity, Model model){
        Users user = usersService.getCurrentUser();
        if (user != null) {
            jobPostActivity.setUsersId(user);
        }

        jobPostActivity.setPostedDate(new Date());
        model.addAttribute("jobPostActivity",jobPostActivity);
        JobPostActivity saved = jobPostActivityService.addNew(jobPostActivity);
        return "redirect:/dashboard/";
    }

    @PostMapping("dashboard/edit/{id}")
    public String editJob(@PathVariable("id") int id, Model model){

        JobPostActivity jobPostActivity= jobPostActivityService.getOne(id);
        model.addAttribute("jobPostActivity",jobPostActivity);
        model.addAttribute("user", usersService.getCurrentUserProfile());
        return "add-jobs";

    }



}
