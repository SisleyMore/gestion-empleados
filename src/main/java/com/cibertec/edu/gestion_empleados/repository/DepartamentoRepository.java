package com.cibertec.edu.gestion_empleados.repository;

import com.cibertec.edu.gestion_empleados.entity.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
}
