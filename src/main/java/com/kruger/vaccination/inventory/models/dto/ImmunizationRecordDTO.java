package com.kruger.vaccination.inventory.models.dto;

import com.kruger.vaccination.inventory.models.entity.Vaccine;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Schema(
        name = "ACTUALIZAR REGISTRO DE VACUNAS DEL EMPLEADO",
        description = "OBJETO DE TRANSFERENCIA DE DATOS USADO PARA LA ACTUALIZAR DE UN EMPLEADO REGISTRADO EN BASE DE DATOS."
)
public class ImmunizationRecordDTO implements Serializable {

    @Schema(
            name = "immunizationRecordId",
            description = "VALOR ÚNICO EN LA BASE DE DATOS, USADO PARA PODER IDENTIFICAR Y DIFERENCIAR UN OBJETO DE" +
                    "OTR, SI SE REQUIERE PERSISTIR UN NUEVO REGISTRO DE VACUNA SE DEBE ENVIAR ESTE VALOR COMO NULO," +
                    "DE LO CONTRARIO ESTE VALOR SE ACTUALIZARA",
            example = "1"
    )
    private Long immunizationRecordId;

    @Schema(
            name = "vaccinationDate",
            description = "FECHA EN LA QUE SE APLICO LA DOSIS",
            example = "2022-12-09"
    )
    private Date vaccinationDate;

    @Schema(
            name = "dosesNumber",
            description = "NUMERO DE DOSIS APLICADA.",
            example = "1"
    )
    private int dosesNumber;

    @Schema(
            name = "vaccine",
            description = "NUMERO DE DOSIS QUE SE HA APLICADO.",
            implementation = VaccineDTO.class
    )
    private VaccineDTO vaccine;

}
