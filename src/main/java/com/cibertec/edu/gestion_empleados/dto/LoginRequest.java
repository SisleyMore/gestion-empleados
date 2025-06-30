package com.cibertec.edu.gestion_empleados.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String correo;
    private String password;
}
