package com.kruger.vaccination.inventory.models.entity;


import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "employee")
@Schema(
        name = "EMPLEADOS",
        description = "ENTIDAD QUE SE MAPEA CONTRA LA BASE DE DATOS, ESTA TABLA ES USADA PARA PODER PERSISTIR LA" +
                "INFORMACIÓN DE UN EMPLEADO EN BASE DE DATOS."
)
public class Employee implements Serializable {

    @Id
    @GeneratedValue
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
    public static boolean employeeIdentificationOnlyNumbers(String employeeIdentification) {
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
    public static boolean employeeIdentificationValidation(String employeeIdentification) {
        int suma = 0;
        if (employeeIdentification.length() == 9) {
            return false;
        } else {
            int[] a = new int[employeeIdentification.length() / 2];
            int[] b = new int[(employeeIdentification.length() / 2)];
            int c = 0;
            int d = 1;
            for (int i = 0; i < employeeIdentification.length() / 2; i++) {
                a[i] = Integer.parseInt(String.valueOf(employeeIdentification.charAt(c)));
                c = c + 2;
                if (i < (employeeIdentification.length() / 2) - 1) {
                    b[i] = Integer.parseInt(String.valueOf(employeeIdentification.charAt(d)));
                    d = d + 2;
                }
            }
            for (int i = 0; i < a.length; i++) {
                a[i] = a[i] * 2;
                if (a[i] > 9) {
                    a[i] = a[i] - 9;
                }
                suma = suma + a[i] + b[i];
            }
            int aux = suma / 10;
            int dec = (aux + 1) * 10;
            if ((dec - suma) == Integer.parseInt(String.valueOf(employeeIdentification.charAt(employeeIdentification.length() - 1))))
                return true;
            else return suma % 10 == 0 && employeeIdentification.charAt(employeeIdentification.length() - 1) == '0';
        }
    }

    /***
     * Employee Identification Validation.
     * @param employeeNames variable que contiene el nombre o el apellido del empleado, necesitamos validar que este
     *                      nombre contenga únicamente letras y no caracteres especiales o numéricos.
     * @return isValid tomará un valor de true si el nombre o apellido del empleado cumple con lo especificado y un
     * valor false si el nombre o apellido del empleado no cumple con lo especificado.
     * **/
    public static boolean stringContainsOnlyLetters(String employeeNames) {
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

    public String getEmployeeIdentification() {
        return employeeIdentification;
    }

    public void setEmployeeIdentification(String employeeIdentification) {
        this.employeeIdentification = employeeIdentification;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }
}
