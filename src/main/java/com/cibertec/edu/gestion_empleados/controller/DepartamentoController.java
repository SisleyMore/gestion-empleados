package com.cibertec.edu.gestion_empleados.controller;

import com.cibertec.edu.gestion_empleados.entity.Departamento;
import com.cibertec.edu.gestion_empleados.repository.DepartamentoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departamentos")
@CrossOrigin(origins = "http://localhost:9000")
public class DepartamentoController {

    private final DepartamentoRepository repo;

    public DepartamentoController(DepartamentoRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Departamento> listar() {
        return repo.findAll();
    }

    @PostMapping
    public Departamento crear(@RequestBody Departamento d) {
        return repo.save(d);
    }

    @PutMapping("/{id}")
    public Departamento actualizar(@PathVariable Long id, @RequestBody Departamento d) {
        d.setId(id);
        return repo.save(d);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
