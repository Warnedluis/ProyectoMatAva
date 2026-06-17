package com.UPIIZ.ProyectoMatAva.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.UPIIZ.ProyectoMatAva.Entities.usuarioEntity;
import com.UPIIZ.ProyectoMatAva.Repositories.UsuarioRepository;


@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService{
    

    private final PasswordEncoder passwordEncoder;

    @Autowired
    UsuarioRepository usuarioRepository;

    UsuarioServiceImpl(PasswordEncoder passwordEncoder)
    {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public usuarioEntity RegistrarUsuario(usuarioEntity usuario)
    {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        usuarioEntity usuario = usuarioRepository.findByEmail(email);

        return User.builder()
        .username(usuario.getEmail())
        .password(usuario.getPassword())
        .roles("USER")
        .build();
    }






}
