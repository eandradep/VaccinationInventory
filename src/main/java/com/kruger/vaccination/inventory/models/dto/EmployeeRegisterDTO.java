package com.kruger.vaccination.inventory.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class EmployeeRegisterDTO implements Serializable {

    @Size(min = 10, max = 10)
    @NotEmpty(message = "ESTE CAMPO NO PUEDE CONTENER UN VALOR NULO O EN BLANCO")
    @Schema(
            name = "employeeIdentification",
            description = "CÉDULA DE IDENTIDAD DEL EMPLEADO,  ESTE CAMPO ES USADO COMO IDENTIFICADOR ÚNICO " +
                    "DE LA TABLA YA QUE DOS USUARIOS NO PODRÁN TENER EL MISMO NUMERO DE CÉDULA.",
            example = "0106126154"
    )
    private String employeeIdentification;

    @Size(min = 5, max = 25)
    @NotEmpty(message = "ESTE CAMPO NO PUEDE CONTENER UN VALOR NULO O EN BLANCO")
    @Schema(
            name = "employeeName",
            description = "NOMBRE DEL NUEVO EMPLEADO, ESTE CAMPO PUEDE CONTENER ENTRE 5 A 25 CARACTERES, " +
                    "NO PUEDE SER NULO.",
            example = "EDISON BLADIMIR"
    )
    private String employeeName;
    @Size(min = 5, max = 25)
    @NotEmpty(message = "ESTE CAMPO NO PUEDE CONTENER UN VALOR NULO O EN BLANCO")
    @Schema(
            name = "employeeLastName",
            description = "APELLIDO DEL USUARIO, ESTE CAMPO PUEDE CONTENER ENTRE 5 A 25 CARACTERES, " +
                    "NO PUEDE SER NULO",
            example = "ANDRADE PRIETO"
    )
    private String employeeLastName;

    @Email(message = "DEBE INGRESAR UN CORREO ELECTRÓNICO VALIDO")
    @NotEmpty(message = "ESTE CAMPO NO PUEDE CONTENER UN VALOR NULO O EN BLANCO")
    @Schema(
            name = "employeeEmail",
            description = "APELLIDO DEL USUARIO, ESTE CAMPO PUEDE CONTENER ENTRE 5 A 25 CARACTERES",
            example = "ANDRADE PRIETO"
    )
    private String employeeEmail;

}
