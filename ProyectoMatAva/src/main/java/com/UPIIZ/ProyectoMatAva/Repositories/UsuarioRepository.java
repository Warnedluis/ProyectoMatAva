package com.UPIIZ.ProyectoMatAva.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.UPIIZ.ProyectoMatAva.Entities.usuarioEntity;

@Repository
public interface UsuarioRepository extends JpaRepository<usuarioEntity, Long> {
    usuarioEntity findByEmail(String email);
}
