package com.kruger.vaccination.inventory.models.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "immunization_record")
@Schema(
        name = "RECORD DE VACUNAS",
        description = "ENTIDAD QUE SE MAPEA CONTRA LA BASE DE DATOS, ESTA TABLA ES USADA PARA PODER PERSISTIR EL " +
                "REGISTRO DE VACUNAS DE UN EMPLEADO"
)
public class ImmunizationRecord implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            name = "immunizationRecordId",
            description = "VALOR ÚNICO EN LA BASE DE DATOS, USADO PARA PODER IDENTIFICAR Y DIFERENCIAR UN OBJETO DE OTRO.",
            example = "1"
    )
    private Long immunizationRecordId;

    @Column(name = "vaccination_date")
    @Schema(
            name = "vaccinationDate",
            description = "FECHA EN LA QUE SE APLICO LA DOSIS",
            example = "2022-12-09"
    )
    private Date vaccinationDate;

    @Column(name = "doses_number")
    @Schema(
            name = "dosesNumber",
            description = "NUMERO DE DOSIS APLICADA.",
            example = "1"
    )
    private int dosesNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull(message = "se debe ingresar tipo de vacuna para el registro.")
    @JoinColumn(
            name = "vaccine_id",
            referencedColumnName = "vaccine_id",
            nullable = false
    )
    @Schema(
            name = "vaccine",
            description = "NUMERO DE DOSIS QUE SE HA APLICADO.",
            implementation = Vaccine.class
    )
    private Vaccine vaccine;


}
