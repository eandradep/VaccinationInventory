package com.kruger.vaccination.inventory.models.entity;


import com.kruger.vaccination.inventory.models.dto.EmployeeRegisterDTO;
import com.kruger.vaccination.inventory.models.dto.EmployeeUpdateDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            name = "employeeId",
            description = "VALOR ÚNICO EN LA BASE DE DATOS, USADO PARA PODER IDENTIFICAR Y DIFERENCIAR UN OBJETO DE OTRO.",
            example = "1"
    )
    private Long employeeId;

    @Column(name = "employee_identification", unique = true, nullable = false)
    @Size(min = 10, max = 10, message = "EL CAMPO DEBE CONTENER 10 DÍGITOS")
    @NotEmpty(message = "ESTE CAMPO NO PUEDE CONTENER UN VALOR NULO O EN BLANCO")
    @Schema(
            name = "employeeIdentification",
            description = "CÉDULA DE IDENTIDAD DEL EMPLEADO,  ESTE CAMPO ES USADO COMO IDENTIFICADOR ÚNICO " +
                    "DE LA TABLA YA QUE DOS USUARIOS NO PODRÁN TENER EL MISMO NUMERO DE CÉDULA.",
            example = "0106126154"
    )
    private String employeeIdentification;


    @Column(name = "employee_name", nullable = false)
    @Size(min = 5, max = 25, message = "El campo debe contener entre 5 y 25 dígitos")
    @NotEmpty(message = "Este campo no puede contener un valor nulo o en blanco")
    @Schema(
            name = "employeeName",
            description = "NOMBRE DEL NUEVO EMPLEADO, ESTE CAMPO PUEDE CONTENER ENTRE 5 A 25 CARACTERES, " +
                    "NO PUEDE SER NULO.",
            example = "EDISON BLADIMIR"
    )
    private String employeeName;
    @Column(name = "employee_last_name", nullable = false)
    @Size(min = 5, max = 25, message = "El campo debe contener entre 5 y 25 dígitos")
    @NotEmpty(message = "Este campo no puede contener un valor nulo o en blanco")
    @Schema(
            name = "employeeLastName",
            description = "APELLIDO DEL USUARIO, ESTE CAMPO PUEDE CONTENER ENTRE 5 A 25 CARACTERES, " +
                    "NO PUEDE SER NULO",
            example = "ANDRADE PRIETO"
    )
    private String employeeLastName;

    @Column(name = "employee_email", nullable = false)
    @Email(message = "Debe ingresar un correo electrónico valido")
    @NotEmpty(message = "Este campo no puede contener un valor nulo o en blanco")
    @Schema(
            name = "employeeEmail",
            description = "APELLIDO DEL USUARIO, ESTE CAMPO PUEDE CONTENER ENTRE 5 A 25 CARACTERES",
            example = "ANDRADE PRIETO"
    )
    private String employeeEmail;

//    DATA TO COMPLETE EMPLOYEE REGISTER

    @Column(name = "employee_birth_date")
    @Schema(
            name = "employeeBirthDate",
            description = "FECHA DE NACIMIENTO DEL EMPLEADO",
            example = "2019-08-14T00:00:00.000Z"
    )
    private Date employeeBirthDate;

    @Column(name = "employee_address")
    @Size(max = 75, message = "El campo debe contener entre como maximo 75 dígitos")
    @Schema(
            name = "employeeAddress",
            description = "DIRECCION DE DOMICILIO DEL EMPLEADO",
            example = "Ricaurte el Arenal"
    )
    private String employeeAddress;

    @Column(name = "employee_phone_number")
    @Size(max = 10, message = "El campo debe contener entre como maximo 10 dígitos")
    @Schema(
            name = "employeePhoneNumber",
            description = "DIRECCION DE DOMICILIO DEL EMPLEADO",
            example = "0962738743"
    )
    private String employeePhoneNumber;

    @Column(name = "employee_is_vaccinate")
    @Schema(
            name = "employeeIsVaccinate",
            description = "DIRECCION DE DOMICILIO DEL EMPLEADO",
            example = "True"
    )
    private Boolean employeeIsVaccinate = false;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "employee_id"
    )
    @Schema(
            name = "immunizationRecordList",
            description = "REGISTRO DE VACUNAS DEL EMPLEADO",
            allOf = ImmunizationRecord.class
    )
    private List<ImmunizationRecord> immunizationRecordList;

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
        return !isValid;
    }

    public Map<String, String> validateEmployeeDTO(EmployeeRegisterDTO employeeRegisterDTO) {
        Map<String, String> errorList = new HashMap<>();
        if (!employeeIdentificationOnlyNumbers(employeeRegisterDTO.getEmployeeIdentification()))
            errorList.put("firstPossibility", "La cédula contiene valores no numéricos");
        if (!identificationValidation(employeeRegisterDTO.getEmployeeIdentification()))
            errorList.put("secondPossibility", "La cédula es invalida");
        if (stringContainsOnlyLetters(employeeRegisterDTO.getEmployeeName()))
            errorList.put("thirdPossibility", "El nombre de usuario contiene números o caracteres especiales");
        if (stringContainsOnlyLetters(employeeRegisterDTO.getEmployeeLastName()))
            errorList.put("fourthPossibility", "El apellido de usuario contiene números o caracteres especiales");
        return errorList;
    }

    /***
     * Employee Identification Validation.
     * @param employeeUpdateDTO objeto que contiene todos los valores que se pueden actualizar, este objeto contiene
     *                          dos valores de interés, el estado de vacunado y el registro de vacunas, para poder
     *                          proceder con la actualización, se debe validar que el usuario contenga registros de
     *                          vacunas su el estado del empleado es vacunado.
     * @return isValid tomará un valor de true si el usuario cumple con la validación previa.
     * **/
    public Map<String, String> validateEmployeeUpdateDTO(EmployeeUpdateDTO employeeUpdateDTO) {
        Map<String, String> errorList = new HashMap<>();
        if (Boolean.TRUE.equals(employeeUpdateDTO.getEmployeeIsVaccinate()) && employeeUpdateDTO.getImmunizationRecordList().isEmpty()){
            errorList.put("firstPossibility", "El usuario no puede tener estado vacunado sin tener registro de vacunas.");
        }
        return errorList;
    }


}
