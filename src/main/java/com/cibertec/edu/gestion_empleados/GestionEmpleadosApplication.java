package com.cibertec.edu.gestion_empleados;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling

public class GestionEmpleadosApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionEmpleadosApplication.class, args);
	}

}
