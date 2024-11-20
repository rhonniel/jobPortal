package com.practice.jobportal.util;

import com.practice.jobportal.entity.Users;
import com.practice.jobportal.entity.UsersType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


    /*TODO OJO: creamos nuestra propia clase de user details sobreescribiendo los metodos de la interfaz de spring
    * Definimos los autoritis, permisos, perfil, el valor del pass, del username, si el usario se blokea por tiempo o no ect
    * en resumen esto ayuda darle nuestra entidad usaurio a spring security para el manejar su session y logeo
    *
    *  */
public class CustomUserDetails implements UserDetails {

    private final Users user;

    public CustomUserDetails(Users users) {
        this.user = users;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UsersType usersType = user.getUserType();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(usersType.getUserTypeName()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
