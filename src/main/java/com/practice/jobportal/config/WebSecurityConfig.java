package com.practice.jobportal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {


    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    //TODO OJO: de esta forma se indica que le damos acceso a estas rutas y recursos
    private final String[] publicUrl = {"/",
            "/global-search/**",
            "/register",
            "/register/**",
            "/webjars/**",
            "/resources/**",
            "/assets/**",
            "/css/**",
            "/summernote/**",
            "/js/**",
            "/*.css",
            "/*.js",
            "/*.js.map",
            "/fonts**", "/favicon.ico", "/resources/**", "/error"};


    public WebSecurityConfig(CustomUserDetailsService customUserDetailsService,CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.customUserDetailsService = customUserDetailsService;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

      //TODO OJO :  http.authenticationProvider definimos la forma como va a manejar la auteticacion del usuario
        http.authenticationProvider(authenticationProvider());

        /*TODO OJO: http.authorizeHttpRequests con esto  indicamos un fltro  url en las request son validos y que no  o si alguna
        *  o tambien para configurar el acceso a esa url , como el login donde se indica que hacer cuando se entra a esa pagina y que no
        */
        http.authorizeHttpRequests(auth ->{
            auth.requestMatchers(publicUrl).permitAll();
            auth.anyRequest().authenticated();
        });
        http.formLogin(form->form.loginPage("/login").permitAll()
                .successHandler(customAuthenticationSuccessHandler))
                .logout(logout -> {
                    logout.logoutUrl("/logout");
                    logout.logoutSuccessUrl("/");
                }).cors(Customizer.withDefaults()) // se configura el cors por defecto
                .csrf(AbstractHttpConfigurer::disable);// se desabilita la csrf para no estar jodiendo con ella, formularios de thyleaf es remoendable, pero apis rest no es necesaria por JWT


        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        //TODO OJO : PasswordEncoder con este le indicamos como sera manejado el encritamiento de la contraseña
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        authenticationProvider.setUserDetailsService(customUserDetailsService);
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
