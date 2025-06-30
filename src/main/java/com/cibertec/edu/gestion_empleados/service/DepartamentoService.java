package com.cibertec.edu.gestion_empleados.service;

import com.cibertec.edu.gestion_empleados.entity.Departamento;
import com.cibertec.edu.gestion_empleados.repository.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartamentoService {

    @Autowired
    private DepartamentoRepository repo;

    public List<Departamento> listar() {
        return repo.findAll();
    }

    public Departamento crear(Departamento d) {
        return repo.save(d);
    }

    public Departamento actualizar(Long id, Departamento d) {
        d.setId(id);
        return repo.save(d);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
