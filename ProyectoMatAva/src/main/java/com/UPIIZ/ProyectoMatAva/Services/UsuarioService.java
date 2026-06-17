package com.UPIIZ.ProyectoMatAva.Services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.UPIIZ.ProyectoMatAva.Entities.usuarioEntity;

@Service
public interface UsuarioService {
    usuarioEntity RegistrarUsuario(usuarioEntity usuario);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    
}
