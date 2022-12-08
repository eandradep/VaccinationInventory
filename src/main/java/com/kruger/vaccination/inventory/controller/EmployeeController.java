package com.kruger.vaccination.inventory.controller;

import com.kruger.vaccination.inventory.models.dto.EmployeeRegisterDTO;
import com.kruger.vaccination.inventory.models.entity.Employee;
import com.kruger.vaccination.inventory.models.services.employee.IEmployeeService;
import com.kruger.vaccination.inventory.models.services.validation.IValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kruger.vaccination.inventory.configuration.StaticValues.*;

@Tag(
        name = "CONTROLADOR DE EMPLEADOS",
        description = "ESTE CONTROLADOR SE ENCARGA DE ADMINISTRAR EL CRUD DE LA TABLA EMPLEADOS, SEGÚN EL ROL DEL" +
                "USUARIO, SE PERMITIRÁ LA POSIBILIDAD DE CREAR, ACTUALIZAR O LISTAR LA INFORMACIÓN DE LOS EMPLEADOS" +
                "EN BASE A LOS REQUERIMIENTOS DEL CLIENTE."
)
@RestController
@RequestMapping("/employeeController")
public class EmployeeController {


    @Autowired
    private IValidationService iValidationService;
    @Autowired
    private ModelMapper modelMapper;

    @Operation(
            summary = "REGISTRAR INFORMACIÓN BÁSICA DE USUARIO",
            description = "ESTE SERVICIO SE ENCARGA DE PERSISTIR UN NUEVO EMPLEADO EN LA BASE DE DATOS, SEGÚN EL" +
                    "REQUERIMIENTO SE DEBE PODER REGISTRAR, ACTUALIZAR, LISTAR Y ELIMINAR LOS REGISTROS DE EMPLEADOS",
            parameters = {@Parameter(
                    name = "employeeRegisterDTO",
                    description = "DTO QUE PERMITE REALIZAR LAS PRIMERAS VALIDACIONES AL MOMENTO DE REGISTRAR UN" +
                            "EMPLEADO",
                    schema = @Schema(
                            implementation = EmployeeRegisterDTO.class
                    ),
                    required = true
            )},
            method = "POST"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "SI EL OBJETO CUMPLE CON LAS VALIDACIONES PREVIAS SERÁ ALMACENADO EN LA " +
                                    "BASE DE DATOS.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = EmployeeRegisterDTO.class
                                            )
                                    )}
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "SI EL OBJETO NO CUMPLE CON LAS VALIDACIONES PREVIAS RETORNARA UN LISTADO"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "SI EXISTE UN ERROR EN EL SERVIDOR RETORNARA ESTE OBJETO"
                    )
            })
    @PostMapping("/saveEmployee")
    public ResponseEntity<Map<String, Object>> saveEmployee(
            @Valid @RequestBody EmployeeRegisterDTO employeeRegisterDTO, BindingResult result
    ) {
        Employee employeeToPersist = new Employee();
        response = new HashMap<>();
        if (result.hasErrors()) {
            response.put(MESSAGE_VALUE, MESSAGE_VALUE_BAD_REQUEST);
            response.put(ERROR_VALUE, iValidationService.validationDTO(result.getFieldErrors()));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        Map<String, String> responseAux = employeeToPersist.validateEmployeeDTO(employeeRegisterDTO);
        if (!responseAux.isEmpty()) {
            response.put(MESSAGE_VALUE, MESSAGE_VALUE_BAD_REQUEST);
            response.put(ERROR_VALUE, responseAux);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        Employee newEmployee;
        employeeToPersist = modelMapper.map(employeeRegisterDTO, Employee.class);
        try {
            newEmployee = iEmployeeService.saveEmployee(employeeToPersist);
        } catch (Exception exception) {
            return iValidationService.employeeValidations(exception);
        }
        response.put(MESSAGE_VALUE, MESSAGE_VALUE_DATA_SAVE);
        response.put(RESPONSE_VALUE, newEmployee);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "LISTAR TODOS LOS EMPLEADOS",
            description = "ESTE SERVICIO SE ENCARGA DE LISTAR TODOS LOS REGISTROS DE EPLEADO QUE SE ENCEUNTREN " +
                    "EN LA BASE DE DATOS.",
            method = "GET"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "SI SE ENCUENTRAN REGISTROS EN LA BASE DE DATOS",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    allOf = EmployeeRegisterDTO.class
                                            )
                                    )}
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "SI NO SE ENCUENTRAN REGISTROS EN LA BASE DE DATOS, EL SERVICIO RETORNARA EL" +
                                    "CÓDIGO 404 SIN RESULTADOS"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "SI EXISTE UN ERROR EN EL SERVIDOR RETORNARA ESTE OBJETO"
                    )
            })
    @GetMapping("/findAllEmployees")
    public ResponseEntity<Map<String, Object>> findAllEmployees() {
        List<Employee> employees;
        response = new HashMap<>();
        try {
            employees = iEmployeeService.findAllEmployees();
        } catch (Exception exception) {
            return iValidationService.employeeValidations(exception);
        }
        if (employees.isEmpty()) {
            response.put(MESSAGE_VALUE, MESSAGE_VALUE_DATA_NOT_FOUND);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            response.put(MESSAGE_VALUE, MESSAGE_VALUE_DATA_FOUND);
            response.put(RESPONSE_VALUE, employees);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @Operation(
            summary = "BUSCAR UN EMPLEADO POR SU NUMERO DE CÉDULA",
            description = "ESTE SERVICIO SE ENCARGA DE BUSCAR UN EMPLEADO POR SU NUMERO DE CÉDULA.",
            parameters = {@Parameter(
                    name = "employeeIdentification",
                    description = "CÉDULA DE IDENTIDAD DE UN EMPLEADO",
                    example = "0106126154",
                    required = true
            )},
            method = "GET"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "SI SE ENCUENTRAN REGISTROS EN LA BASE DE DATOS",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    allOf = EmployeeRegisterDTO.class
                                            )
                                    )}
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "SI NO SE ENCUENTRAN REGISTROS EN LA BASE DE DATOS, EL SERVICIO RETORNARA EL" +
                                    "CÓDIGO 404 SIN RESULTADOS"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "SI EXISTE UN ERROR EN EL SERVIDOR RETORNARA ESTE OBJETO"
                    )
            })
    @GetMapping("/findAllEmployeeByIdentification/{employeeIdentification}")
    public ResponseEntity<Map<String, Object>> findAllEmployeeByIdentification(
            @PathVariable String employeeIdentification
    ) {
        Employee employee;
        response = new HashMap<>();
        try {
            employee = iEmployeeService.findEmployeeByIdentification(employeeIdentification);
        } catch (Exception exception) {
            return iValidationService.employeeValidations(exception);
        }
        if (employee == null) {
            response.put(MESSAGE_VALUE, MESSAGE_VALUE_DATA_NOT_FOUND);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            response.put(MESSAGE_VALUE, MESSAGE_VALUE_DATA_FOUND);
            response.put(RESPONSE_VALUE, employee);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

}
