package com.cibertec.edu.gestion_empleados.controller;
import com.cibertec.edu.gestion_empleados.config.JwtUtil;
import com.cibertec.edu.gestion_empleados.dto.LoginRequest;
import org.springframework.http.ResponseEntity;

import com.cibertec.edu.gestion_empleados.entity.Usuario;
import com.cibertec.edu.gestion_empleados.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/registro")
    public Usuario registrar(@RequestBody Usuario usuario) {
        return usuarioService.registrarUsuario(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        boolean acceso = usuarioService.loginUsuario(loginRequest.getCorreo(), loginRequest.getPassword());

        if (acceso) {
            String token = jwtUtil.generateToken(loginRequest.getCorreo());
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }
}
