package com.UPIIZ.ProyectoMatAva.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.UPIIZ.ProyectoMatAva.Services.UsuarioServiceImpl;

@Configuration
@EnableWebSecurity
public class springConfig {

    @Autowired
    @Lazy
    UsuarioServiceImpl usuarioServiceImpl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http

        .csrf(csrf -> csrf
            .ignoringRequestMatchers("/Complejos/api/Complejos")
        )
        
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/auth/**", "/js/**", "/error", "/Complejos/api/Complejos").permitAll()
            .anyRequest().authenticated()
        )
        .formLogin(login -> login
            .loginPage("/auth/getInicioSesion")
            .loginProcessingUrl("/auth/getInicioSesion")
            .defaultSuccessUrl("/getInicio",true)
            .permitAll()
        )
        .logout(logout-> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/auth/getInicio")
            .permitAll()
        );


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider((UserDetailsService) usuarioServiceImpl);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    
}
