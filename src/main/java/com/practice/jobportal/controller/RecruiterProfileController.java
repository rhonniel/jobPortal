package com.practice.jobportal.controller;

import com.practice.jobportal.entity.RecruiterProfile;
import com.practice.jobportal.entity.Users;
import com.practice.jobportal.repository.UsersRepository;
import com.practice.jobportal.services.RecruiterProfileService;
import com.practice.jobportal.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {

    private final UsersRepository usersRepository;
    private final RecruiterProfileService recruiterProfileService;


    @Autowired
    public RecruiterProfileController(UsersRepository usersRepository, RecruiterProfileService recruiterProfileService) {
        this.usersRepository = usersRepository;
        this.recruiterProfileService = recruiterProfileService;
    }




    @GetMapping("/")
    public String recruiterProfile(Model model){
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUsername = authentication.getName();
           Users users= usersRepository.findByEmail(currentUsername).orElseThrow(()-> new UsernameNotFoundException("Could not " +
                    "found user"));
            Optional<RecruiterProfile> recruiterProfile = recruiterProfileService.getOne(users.getUserId());

            recruiterProfile.ifPresent(profile -> model.addAttribute("profile", profile));

        }
        return "recruiter_profile";
    }


    @PostMapping("/addNew")
    public String addNew(RecruiterProfile recruiterProfile, @RequestParam("image")MultipartFile multipartFile, Model model){
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUsername = authentication.getName();
            Users user=usersRepository.findByEmail(currentUsername).orElseThrow(()->
                    new UsernameNotFoundException("Could"+ "not found user"));
            recruiterProfile.setUserId(user);
            recruiterProfile.setUserAccountId(user.getUserId());

        }

        model.addAttribute("profile", recruiterProfile);
        String fileName = "";
        if(!Objects.requireNonNull(multipartFile.getOriginalFilename()).isEmpty()){
            fileName= StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            recruiterProfile.setProfilePhoto(fileName);
        }
        RecruiterProfile saveUser = recruiterProfileService.addNew(recruiterProfile);

        String uploadDir = "photos/recruiter/"+ saveUser.getUserAccountId();

        try {
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } catch (Exception e){
            e.printStackTrace();
        }

        return "redirect:/dashboard/";
    }

}
