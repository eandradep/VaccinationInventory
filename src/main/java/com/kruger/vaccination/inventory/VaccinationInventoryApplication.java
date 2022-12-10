package com.kruger.vaccination.inventory;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
public class VaccinationInventoryApplication  implements CommandLineRunner {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private final Logger logger = LoggerFactory.getLogger(VaccinationInventoryApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(VaccinationInventoryApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        logger.debug("Subproceso que encripta el password de un usuarios");
//        String password = "rgavilanesKruger2022";
        String password = "seleccionKruger2022";
        System.out.println(passwordEncoder.encode(password));

    }
}
