package com.kruger.vaccination.inventory.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(
        name = "OBJETO DTO QUE MUESTRA EL ID DE LA VACUNA EN EL REGISTRO DE EMPLEADO",
        description = "OBJETO DE TRANSFERENCIA DE DATOS USADO PARA LA ACTUALIZAR DE UN EMPLEADO REGISTRADO EN BASE DE DATOS."
)
public class VaccineDTO implements Serializable {

    @Schema(
            name = "vaccineId",
            description = "VALOR ÚNICO EN LA BASE DE DATOS, USADO PARA PODER IDENTIFICAR Y DIFERENCIAR UN OBJETO DE OTRO.",
            example = "1"
    )
    private Long vaccineId;

}
