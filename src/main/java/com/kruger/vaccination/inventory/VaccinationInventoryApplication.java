package com.kruger.vaccination.inventory;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Vaccination Inventory",
                version = "1.0",
                description = "ESTE SERVICIO LA SOLUCIÓN AL RETO PROPUESTO POR KRUGER CORP SOBRE UNA APLICATIVO BACK END" +
                        "PARA ADMINISTRAR INVENTARIO DE VACUNACIÓN DE EMPLEADOS",
                contact = @Contact(
                        name = "EDISON ANDRADE",
                        email = "eandradep@est.ups.edu.ec"
                )
        )
)
public class VaccinationInventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(VaccinationInventoryApplication.class, args);
    }

}
