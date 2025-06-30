package com.cibertec.edu.gestion_empleados.service;

import com.cibertec.edu.gestion_empleados.entity.Usuario;
import com.cibertec.edu.gestion_empleados.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Usuario registrarUsuario(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public boolean loginUsuario(String correo, String password) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByCorreo(correo);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            return passwordEncoder.matches(password, usuario.getPassword());
        }
        return false;
    }

}