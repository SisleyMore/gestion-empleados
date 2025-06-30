package com.cibertec.edu.gestion_empleados.repository;

import com.cibertec.edu.gestion_empleados.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
}
