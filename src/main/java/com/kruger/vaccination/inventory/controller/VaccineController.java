package com.kruger.vaccination.inventory.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "CONTROLADOR DE VACUNAS",
        description = "ESTE CONTROLADOR SE ENCARGA DE ADMINISTRAR EL CRUD DE LA TABLA VACUNAS, SEGÚN EL ROL DEL" +
                "USUARIO, SE PERMITIRÁ LA POSIBILIDAD DE CREAR, ACTUALIZAR O LISTAR LA INFORMACIÓN DE LAS VACUNAS" +
                "EN BASE A LOS REQUERIMIENTOS DEL CLIENTE."
)
@RestController
@RequestMapping("/vaccineController")
public class VaccineController {
}
