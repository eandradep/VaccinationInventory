package com.kruger.vaccination.inventory.models.dto;

import com.kruger.vaccination.inventory.models.entity.ImmunizationRecord;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Schema(
        name = "ACTUALIZAR EMPLEADOS DTO",
        description = "OBJETO DE TRANSFERENCIA DE DATOS USADO PARA LA ACTUALIZAR DE UN EMPLEADO REGISTRADO EN BASE DE DATOS."
)
public class EmployeeUpdateDTO implements Serializable {

    @Size(min = 5, max = 25, message = "El campo debe contener entre 5 y 25 dígitos")
    @NotEmpty(message = "Este campo no puede contener un valor nulo o en blanco")
    @Schema(
            name = "employeeName",
            description = "NOMBRE DEL NUEVO EMPLEADO, ESTE CAMPO PUEDE CONTENER ENTRE 5 A 25 CARACTERES, " +
                    "NO PUEDE SER NULO.",
            example = "EDISON BLADIMIR"
    )
    private String employeeName;

    @Size(min = 5, max = 25, message = "El campo debe contener entre 5 y 25 dígitos")
    @NotEmpty(message = "Este campo no puede contener un valor nulo o en blanco")
    @Schema(
            name = "employeeLastName",
            description = "APELLIDO DEL USUARIO, ESTE CAMPO PUEDE CONTENER ENTRE 5 A 25 CARACTERES, " +
                    "NO PUEDE SER NULO",
            example = "ANDRADE PRIETO"
    )
    private String employeeLastName;

//    @NotEmpty(message = "Este campo no puede contener un valor nulo o en blanco")
    @Schema(
            name = "employeeBirthDate",
            description = "FECHA DE NACIMIENTO DEL EMPLEADO",
            example = "2019-08-14"
    )
    private Date employeeBirthDate;

    @NotEmpty(message = "Este campo no puede contener un valor nulo o en blanco")
    @Size(max = 75, message = "El campo debe contener entre como maximo 75 dígitos")
    @Schema(
            name = "employeeAddress",
            description = "DIRECCION DE DOMICILIO DEL EMPLEADO",
            example = "Ricaurte el Arenal"
    )
    private String employeeAddress;

    @NotEmpty(message = "Este campo no puede contener un valor nulo o en blanco")
    @Size(max = 10, message = "El campo debe contener entre como maximo 10 dígitos")
    @Schema(
            name = "employeePhoneNumber",
            description = "DIRECCION DE DOMICILIO DEL EMPLEADO",
            example = "0962738743"
    )
    private String employeePhoneNumber;

//    @NotEmpty(message = "Este campo no puede contener un valor nulo o en blanco")
    @Schema(
            name = "employeeIsVaccinate",
            description = "ESTADO DE VACUNACION DEL EMPLEADO",
            example = "True"
    )
    private Boolean employeeIsVaccinate;

    @NotEmpty(message = "Este campo no puede contener un valor nulo o en blanco")
    @Schema(
            name = "immunizationRecordList",
            description = "REGISTRO DE VACUNAS DEL EMPLEADO",
            allOf = ImmunizationRecordDTO.class
    )
    private List<ImmunizationRecordDTO> immunizationRecordList;
}
