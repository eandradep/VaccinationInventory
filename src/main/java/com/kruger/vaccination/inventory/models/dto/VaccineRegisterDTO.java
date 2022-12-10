package com.kruger.vaccination.inventory.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Schema(
        name = "VACUNA DTO",
        description = "OBJETO DE TRANSFERENCIA DE DATOS USADO PARA LA PERSISTENCIA DE UNA NUEVA VACUNA."  
)
public class VaccineRegisterDTO implements Serializable {

    @Size(min = 5, max = 15, message = "El campo debe contener entre 5 y 15 dígitos")
    @NotEmpty(message = "Este campo no puede contener un valor nulo o en blanco")
    @Schema(
            name = "vaccineName",
            description = "NOMBRE DEL TIPO DE VACUNA.",
            example = "AstraZeneca"
    )
    private String vaccineName;

}
