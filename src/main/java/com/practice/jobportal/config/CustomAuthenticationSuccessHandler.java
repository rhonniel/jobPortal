package com.practice.jobportal.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


/*
* TODO OJO: Componente para configurar el acceso de un usuario
*  ya configuraste como el userdetails obtenia la data del usuario ahora faltaba
* que espesificara la forma de validar su logeo de su perfil, podrias redigir
* cada perfil a x lugar o filtrar por alguno
*
* */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails= (UserDetails)  authentication.getPrincipal();
        String username = userDetails.getUsername();
        System.out.println("the username "+ username+" is logged in.");
        boolean hasJobSeekerRole=authentication.getAuthorities().stream().anyMatch(
                r -> r.getAuthority().equals("Job Seeker"));
        boolean hasRecruiterRole = authentication.getAuthorities().stream().anyMatch(
                r -> r.getAuthority().equals("Recruiter"));
        if(hasRecruiterRole || hasJobSeekerRole){
            response.sendRedirect("/dashboard/");
        }

    }
}
