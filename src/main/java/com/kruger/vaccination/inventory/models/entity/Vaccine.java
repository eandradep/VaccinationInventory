package com.kruger.vaccination.inventory.models.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Table(name = "vaccine")
@Schema(
        name = "TIPO DE VACUNA",
        description = "ENTIDAD QUE SE MAPEA CONTRA LA BASE DE DATOS, ESTA TABLA ES USADA PARA PODER PERSISTIR LA" +
                "INFORMACIÓN DE LOS TIPOS DE VACUNA"
)
public class Vaccine implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            name = "employeeIdentification",
            description = "VALOR ÚNICO EN LA BASE DE DATOS, USADO PARA PODER IDENTIFICAR Y DIFERENCIAR UN OBJETO DE OTRO.",
            example = "1"
    )
    private Long vaccineId;

    @Column(name = "vaccine_name", nullable = false)
    @Size(min = 5, max = 15, message = "El campo debe contener entre 5 y 15 dígitos")
    @NotEmpty(message = "Este campo no puede contener un valor nulo o en blanco")
    @Schema(
            name = "vaccineTypeName",
            description = "NOMBRE DEL TIPO DE VACUNA.",
            example = "AstraZeneca"
    )
    private String vaccineName;

}
