package com.practice.jobportal.controller;

import com.practice.jobportal.entity.Users;
import com.practice.jobportal.entity.UsersType;
import com.practice.jobportal.services.UsersService;
import com.practice.jobportal.services.UsersTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class UsersController {

    private final UsersTypeService usersTypeService;
    private final UsersService usersService;
    @Autowired
    public UsersController(UsersTypeService usersTypeService, UsersService usersService){
        this.usersService=usersService;
        this.usersTypeService=usersTypeService;
    }

    @GetMapping("/register")
    public String register(Model model){
        List<UsersType> usersTypes = usersTypeService.getAll();
        model.addAttribute("getAllTypes", usersTypes);
        model.addAttribute("user", new Users());

        return "register";

    }

    @PostMapping("/register/new")
    public String userRegistration(@Valid Users users, Model model){
    //    System.out.println("User:: "+users);
        Optional<Users> optionalUsers = usersService.getUserByEmail(users.getEmail());
        if(optionalUsers.isPresent()){
            // TODO OJO: se replica el codigo de regristrar dadoq ue el posr hara  que la pagina se recargue y perdera la informacion necesaria para usar el formulario
            model.addAttribute("error", "Email already registered, try to login or register with other email.");
            List<UsersType> usersTypes = usersTypeService.getAll();
            model.addAttribute("getAllTypes", usersTypes);
            model.addAttribute("user", new Users());
            return "register";
        }
        usersService.addNew(users);
        return "dashboard";
    }


}
