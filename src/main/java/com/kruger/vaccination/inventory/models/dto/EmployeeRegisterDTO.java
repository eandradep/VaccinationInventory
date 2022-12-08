package com.kruger.vaccination.inventory.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class EmployeeRegisterDTO implements Serializable {

    @Size(min = 10, max = 10, message = "EL CAMPO DEBE CONTENER 10 DÍGITOS")
    @NotEmpty(message = "ESTE CAMPO NO PUEDE CONTENER UN VALOR NULO O EN BLANCO")
    @Schema(
            name = "employeeIdentification",
            description = "CÉDULA DE IDENTIDAD DEL EMPLEADO,  ESTE CAMPO ES USADO COMO IDENTIFICADOR ÚNICO " +
                    "DE LA TABLA YA QUE DOS USUARIOS NO PODRÁN TENER EL MISMO NUMERO DE CÉDULA.",
            example = "1721350252"
    )
    private String employeeIdentification;

    @Size(min = 5, max = 25,  message = "El campo debe contener entre 5 y 25 dígitos")
    @NotEmpty(message = "Este campo no puede contener un valor nulo o en blanco")
    @Schema(
            name = "employeeName",
            description = "Nombre del nuevo empleado, este campo puede contener entre 5 a 25 caracteres, " +
                    "no puede ser nulo.",
            example = "ANDREA"
    )
    private String employeeName;

    @Size(min = 5, max = 25, message = "El campo debe contener entre 5 y 25 dígitos")
    @NotEmpty(message = "Este campo no puede contener un valor nulo o en blanco")
    @Schema(
            name = "employeeLastName",
            description = "Apellido del usuario, este campo puede contener entre 5 a 25 caracteres, no puede ser nulo",
            example = "RODRIGUEZ"
    )
    private String employeeLastName;

    @Email(message = "Debe ingresar un correo electrónico valido")
    @NotEmpty(message = "Este campo no puede contener un valor nulo o en blanco")
    @Schema(
            name = "employeeEmail",
            description = "APELLIDO DEL USUARIO, ESTE CAMPO PUEDE CONTENER ENTRE 5 A 25 CARACTERES",
            example = "andrea@gmail.com"
    )
    private String employeeEmail;

}
