package com.cibertec.edu.gestion_empleados.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String puesto;

    private Double salario;
    @ManyToOne
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;

}
