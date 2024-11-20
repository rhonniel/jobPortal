package com.practice.jobportal.config;

import com.practice.jobportal.entity.Users;
import com.practice.jobportal.repository.UsersRepository;
import com.practice.jobportal.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    /*TODO OJO: Este servicio ayuda a definir las informaciones del usuario que se estara logeando
    * Implementamos  una interfaz que nos da los constracto para este tipo de servicios ya definidos por spring
    * */

    private final UsersRepository usersRepository;

    @Autowired
    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

        /*TODO OJO: el usuario que cargamos  lo colocamos en un objeto nuevo que implementa la interfaz userdetail de Spring*/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Users users =usersRepository.findByEmail(username).orElseThrow(() ->
               new UsernameNotFoundException("Could not found user"));
       return new CustomUserDetails(users);
    }
}
