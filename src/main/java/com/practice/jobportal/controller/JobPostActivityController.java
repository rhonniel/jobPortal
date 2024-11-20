package com.practice.jobportal.controller;

import com.practice.jobportal.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JobPostActivityController {

    private final UsersService usersService;

    @Autowired
    public JobPostActivityController (UsersService usersService){
        this.usersService = usersService;
    }

    @GetMapping("/dashboard/")
    public String searchJobs(Model model){

        Object currentUserProfile = usersService.getCurrentUserProfile();
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        // TODO OJO: yo creo que  el preguinta si el usario esta autenticado no entra en la pregunta sino entonces si no le llego mucho o es alrevez
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUserName = authentication.getName();
            model.addAttribute("username", currentUserName);
        }
        model.addAttribute("user",currentUserProfile);

        return "dashboard";

    }

}
