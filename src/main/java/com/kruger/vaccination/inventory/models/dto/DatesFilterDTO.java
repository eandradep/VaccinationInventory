package com.kruger.vaccination.inventory.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Schema(
        name = "DTO PARA PODER REALIZAR EL FILTRADO DE LA INFORMACIÓN POR FECHAS",
        description = "OBJETO DE TRANSFERENCIA DE DATOS USADO PARA PODER FILTRAR LA INFORMACIÓN POR RANGO DE FECHAS"
)
public class DatesFilterDTO  implements Serializable {

    @Schema(
            name = "startDate",
            description = "FECHA DE INICIO PARA FILTRAR INFORMACIÓN",
            example = "2019-08-10"
    )
    private Date startDate;

    @Schema(
            name = "endDate",
            description = "FECHA FINAL PARA FILTRAR INFORMACIÓN",
            example = "2019-08-14"
    )
    private Date endDate;
}
