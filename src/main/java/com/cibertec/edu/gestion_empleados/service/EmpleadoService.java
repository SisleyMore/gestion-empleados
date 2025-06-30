package com.cibertec.edu.gestion_empleados.service;

import com.cibertec.edu.gestion_empleados.entity.Empleado;
import com.cibertec.edu.gestion_empleados.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<Empleado> listarTodos() {
        return empleadoRepository.findAll();
    }

    public Optional<Empleado> obtenerPorId(Long id) {
        return empleadoRepository.findById(id);
    }

    public Empleado registrar(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    public Empleado actualizar(Long id, Empleado nuevoEmpleado) {
        return empleadoRepository.findById(id)
                .map(empleado -> {
                    empleado.setNombre(nuevoEmpleado.getNombre());
                    empleado.setPuesto(nuevoEmpleado.getPuesto());
                    empleado.setSalario(nuevoEmpleado.getSalario());
                    return empleadoRepository.save(empleado);
                })
                .orElse(null);
    }

    public void eliminar(Long id) {
        empleadoRepository.deleteById(id);
    }
}
