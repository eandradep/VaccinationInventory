package com.kruger.vaccination.inventory.models.entity;


import com.kruger.vaccination.inventory.models.dto.EmployeeRegisterDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Table(name = "employee")
@Schema(
        name = "EMPLEADOS",
        description = "ENTIDAD QUE SE MAPEA CONTRA LA BASE DE DATOS, ESTA TABLA ES USADA PARA PODER PERSISTIR LA" +
                "INFORMACIÓN DE UN EMPLEADO EN BASE DE DATOS."
)
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            name = "employeeIdentification",
            description = "VALOR ÚNICO EN LA BASE DE DATOS, USADO PARA PODER IDENTIFICAR Y DIFERENCIAR UN OBJETO DE OTRO.",
            example = "1"
    )
    private Long employeeId;


    @Size(min = 10, max = 10)
    @NotEmpty(message = "ESTE CAMPO NO PUEDE CONTENER UN VALOR NULO O EN BLANCO")
    @Column(name = "employee_identification", unique = true, nullable = false)
    @Schema(
            name = "employeeIdentification",
            description = "CÉDULA DE IDENTIDAD DEL EMPLEADO,  ESTE CAMPO ES USADO COMO IDENTIFICADOR ÚNICO " +
                    "DE LA TABLA YA QUE DOS USUARIOS NO PODRÁN TENER EL MISMO NUMERO DE CÉDULA.",
            example = "0106126154"
    )
    private String employeeIdentification;


    @Size(min = 5, max = 25)
    @Column(name = "employee_name", nullable = false)
    @NotEmpty(message = "ESTE CAMPO NO PUEDE CONTENER UN VALOR NULO O EN BLANCO")
    @Schema(
            name = "employeeName",
            description = "NOMBRE DEL NUEVO EMPLEADO, ESTE CAMPO PUEDE CONTENER ENTRE 5 A 25 CARACTERES, " +
                    "NO PUEDE SER NULO.",
            example = "EDISON BLADIMIR"
    )
    private String employeeName;

    @Size(min = 5, max = 25)
    @Column(name = "employee_last_name", nullable = false)
    @NotEmpty(message = "ESTE CAMPO NO PUEDE CONTENER UN VALOR NULO O EN BLANCO")
    @Schema(
            name = "employeeLastName",
            description = "APELLIDO DEL USUARIO, ESTE CAMPO PUEDE CONTENER ENTRE 5 A 25 CARACTERES, " +
                    "NO PUEDE SER NULO",
            example = "ANDRADE PRIETO"
    )
    private String employeeLastName;

    @Column(name = "employee_email", nullable = false)
    @Email(message = "DEBE INGRESAR UN CORREO ELECTRÓNICO VALIDO")
    @NotEmpty(message = "ESTE CAMPO NO PUEDE CONTENER UN VALOR NULO O EN BLANCO")
    @Schema(
            name = "employeeEmail",
            description = "APELLIDO DEL USUARIO, ESTE CAMPO PUEDE CONTENER ENTRE 5 A 25 CARACTERES",
            example = "ANDRADE PRIETO"
    )
    private String employeeEmail;

    /***
     * Employee Identification Only Numbers.
     * @param employeeIdentification variable que contiene el número de cédula del empleado, necesitamos validar que la
     *                               cédula contenga únicamente números y no contenga caracteres especiales.
     * @return isValid tomará un valor de true si la cédula del empleado cumple con lo especificado y un valor false
     * si la cédula del empleado cumple no cumple con lo especificado.
     * **/
    private static boolean employeeIdentificationOnlyNumbers(String employeeIdentification) {
        boolean isValid = true;
        if (employeeIdentification.length() == 0) {
            return false;
        }
        for (int x = 0; x < employeeIdentification.length(); x++) {
            char c = employeeIdentification.charAt(x);
            if (!(c >= '0' && c <= '9')) {
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    /***
     * Employee Identification Validation.
     * @param employeeIdentification variable que contiene el número de cédula del empleado, necesitamos validar que la
     *                               cédula, sea una cédula válida para poder continuar con el almacenamiento de los
     *                               datos.
     * @return si la cédula del empleado es correcta, esta retornara un valor boolean true, caso contrario retornará
     * un valor boolean false.
     * **/
    private static boolean identificationValidation(String employeeIdentification) {
        int valueSubString;
        int valueSum = 0;
        int valueAccum;
        int valueRest = 0;
        for (int i = 0; i < employeeIdentification.length() - 1; i++) {
            valueSubString = Integer.parseInt(employeeIdentification.charAt(i) + "");
            if (i % 2 == 0) {
                valueSubString = valueSubString * 2;
                if (valueSubString > 9) {
                    valueSubString = valueSubString - 9;
                }
            }
            valueSum = valueSum + valueSubString;
        }
        if (valueSum % 10 != 0) {
            valueAccum = ((valueSum / 10) + 1) * 10;
            valueRest = valueAccum - valueSum;
        }
        int ultimo = Integer.parseInt(employeeIdentification.charAt(9) + "");
        return ultimo == valueRest;
    }

    /***
     * Employee Identification Validation.
     * @param employeeNames variable que contiene el nombre o el apellido del empleado, necesitamos validar que este
     *                      nombre contenga únicamente letras y no caracteres especiales o numéricos.
     * @return isValid tomará un valor de true si el nombre o apellido del empleado cumple con lo especificado y un
     * valor false si el nombre o apellido del empleado no cumple con lo especificado.
     * **/
    private static boolean stringContainsOnlyLetters(String employeeNames) {
        boolean isValid = true;
        for (int x = 0; x < employeeNames.length(); x++) {
            char c = employeeNames.charAt(x);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ')) {
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    public boolean validateEmployeeDTO(EmployeeRegisterDTO employeeRegisterDTO) {
        return employeeIdentificationOnlyNumbers(employeeRegisterDTO.getEmployeeIdentification()) &&
                identificationValidation(employeeRegisterDTO.getEmployeeIdentification()) &&
                stringContainsOnlyLetters(employeeRegisterDTO.getEmployeeName()) &&
                stringContainsOnlyLetters(employeeRegisterDTO.getEmployeeLastName());
    }

}
