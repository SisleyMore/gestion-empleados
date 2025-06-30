package com.cibertec.edu.gestion_empleados.repository;

import com.cibertec.edu.gestion_empleados.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
}
