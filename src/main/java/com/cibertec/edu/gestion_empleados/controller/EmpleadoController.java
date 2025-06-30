package com.cibertec.edu.gestion_empleados.controller;

import com.cibertec.edu.gestion_empleados.entity.Empleado;
import com.cibertec.edu.gestion_empleados.repository.EmpleadoRepository;
import com.cibertec.edu.gestion_empleados.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.scheduling.annotation.Scheduled;
import java.io.FileOutputStream;
import java.io.File;
import java.time.LocalDate;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.io.FileInputStream;


@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;
    @Autowired
    private EmpleadoRepository repo;

    @GetMapping
    public List<Empleado> listar() {
        return empleadoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtenerPorId(@PathVariable Long id) {
        return empleadoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Empleado crear(@RequestBody Empleado empleado) {
        return empleadoService.registrar(empleado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizar(@PathVariable Long id, @RequestBody Empleado empleado) {
        Empleado actualizado = empleadoService.actualizar(id, empleado);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        empleadoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    @Scheduled(cron = "0 * * * * ?") // cada minuto (solo para pruebas)

    //@Scheduled(cron = "0 0 9 * * ?") // todos los días a las 9am
    //@Scheduled(cron = "0 0 8 1 * ?") // todos los meses, día 1 a las 8am
    public void generarExcelMensual() throws IOException {
        List<Empleado> empleados = repo.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Empleados");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Nombre");
        headerRow.createCell(2).setCellValue("Puesto");
        headerRow.createCell(3).setCellValue("Salario");

        int rowIndex = 1;
        for (Empleado e : empleados) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(e.getId());
            row.createCell(1).setCellValue(e.getNombre());
            row.createCell(2).setCellValue(e.getPuesto());
            row.createCell(3).setCellValue(e.getSalario());
        }

        File carpeta = new File("excel-diario");
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }

        LocalDateTime ahora = LocalDateTime.now();
        String timestamp = ahora.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String nombreArchivo = String.format("empleados-%s.xlsx", timestamp);
        FileOutputStream out = new FileOutputStream("excel-diario/" + nombreArchivo);
        workbook.write(out);
        workbook.close();
        out.close();

        System.out.println("📁 Reporte mensual generado: " + nombreArchivo);
    }

    @GetMapping("/descargar/{archivo}")
    public ResponseEntity<Resource> descargarArchivo(@PathVariable String archivo) throws IOException {
        File file = new File("excel-diario/" + archivo);

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.length())
                .body(resource);
    }
    @GetMapping("/reportes")
    public List<String> listarReportesGenerados() {
        File carpeta = new File("excel-diario");
        String[] archivos = carpeta.list((dir, name) -> name.endsWith(".xlsx"));

        if (archivos == null) {
            return new ArrayList<>();
        }

        Arrays.sort(archivos, Collections.reverseOrder()); // opcional: orden descendente
        return List.of(archivos);
    }


}
