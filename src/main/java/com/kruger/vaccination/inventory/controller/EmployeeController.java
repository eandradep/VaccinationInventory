package com.kruger.vaccination.inventory.controller;

import com.kruger.vaccination.inventory.models.dto.DatesFilterDTO;
import com.kruger.vaccination.inventory.models.dto.EmployeeRegisterDTO;
import com.kruger.vaccination.inventory.models.dto.EmployeeUpdateDTO;
import com.kruger.vaccination.inventory.models.entity.Employee;
import com.kruger.vaccination.inventory.models.entity.UserAccount;
import com.kruger.vaccination.inventory.models.services.employee.IEmployeeService;
import com.kruger.vaccination.inventory.models.services.user.IUsuarioService;
import com.kruger.vaccination.inventory.models.services.validation.IValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private IEmployeeService iEmployeeService;
    @Autowired
    private IUsuarioService iUsuarioService;
    @Autowired
    private IValidationService iValidationService;
    @Autowired
    private ModelMapper modelMapper;

    @Operation(
            summary = "REGISTRAR INFORMACIÓN BÁSICA DE USUARIO {ROLE ADMIN}",
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
    @SecurityRequirement(name = "userAuthorization")
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
                                                    implementation = Employee.class
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
    @Secured({"ROLE_ADMIN"})
    @PostMapping("/saveEmployee")
    @Transactional(rollbackFor = {Exception.class} )
    public ResponseEntity<Map<String, Object>> saveEmployee(
            @Valid @RequestBody EmployeeRegisterDTO employeeRegisterDTO, BindingResult result
    ) {
        Employee employeeToPersist = new Employee();
        Map<String, Object> response = new HashMap<>();
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
        UserAccount  userAccount;
        employeeToPersist = modelMapper.map(employeeRegisterDTO, Employee.class);
        employeeToPersist.setEmployeeIsVaccinate(false);
        try {
            newEmployee = iEmployeeService.saveEmployee(employeeToPersist);
            userAccount = iUsuarioService.saveUserAccount(newEmployee);
        } catch (Exception exception) {
            return iValidationService.employeeValidations(exception);
        }
        response.put(MESSAGE_VALUE, MESSAGE_VALUE_DATA_SAVE);
        response.put(RESPONSE_VALUE, newEmployee);
        response.put(RESPONSE_VALUE_ACCOUNT, userAccount);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN"})
    @Operation(
            summary = "LISTAR TODOS LOS EMPLEADOS {ROLE ADMIN}",
            description = "ESTE SERVICIO SE ENCARGA DE LISTAR TODOS LOS REGISTROS DE EPLEADO QUE SE ENCEUNTREN " +
                    "EN LA BASE DE DATOS.",
            method = "GET"
    )
    @SecurityRequirement(name = "userAuthorization")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "SI SE ENCUENTRAN REGISTROS EN LA BASE DE DATOS",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    allOf = Employee.class
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
        Map<String, Object> response = new HashMap<>();
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
            summary = "LISTAR EMPLEADOS EN BASE A SU ESTADO DE VACUNACIÓN {ROLE ADMIN}",
            description = "ESTE SERVICIO SE ENCARGA DE LISTAR TODOS LOS REGISTROS DE EMPLEADO QUE SE ENCUENTREN " +
                    "EN LA BASE DE DATOS, SIEMPRE QUE CUMPLAN CON EL ESTADO PROPORCIONADO.",
            method = "GET",
            parameters = {
                    @Parameter(name = "vaccineStatus",
                            description = "BOOLEAN QUE CONTIENE EL ESTADO A FILTRAR.",
                            example = "true")
            }
    )
    @SecurityRequirement(name = "userAuthorization")
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
    @Secured({"ROLE_ADMIN"})
    @GetMapping("/findEmployeesVaccineStatus/{vaccineStatus}")
    public ResponseEntity<Map<String, Object>> findEmployeesVaccineStatus(
            @PathVariable Boolean vaccineStatus) {
        List<Employee> employees;
        Map<String, Object> response = new HashMap<>();
        try {
            employees = iEmployeeService.findAllEmployeesByVaccineStatus(vaccineStatus);
        } catch (Exception exception) {
            return iValidationService.employeeValidations(exception);
        }
        if (employees.isEmpty()) {
            response.put(MESSAGE_VALUE, MESSAGE_VALUE_DATA_NOT_FOUND);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            response.put(MESSAGE_VALUE, MESSAGE_VALUE_DATA_FOUND);
            response.put(RESPONSE_VALUE, employees
                    .stream()
                    .map(user -> modelMapper.map(user, EmployeeRegisterDTO.class))
                    .collect(Collectors.toList()));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @Secured({"ROLE_ADMIN"})
    @Operation(
            summary = "LISTAR EMPLEADOS EN BASE AL TIPO DE VACUNA {ROLE ADMIN}",
            description = "ESTE SERVICIO SE ENCARGA DE LISTAR TODOS LOS REGISTROS DE EMPLEADO QUE SE ENCUENTREN " +
                    "EN LA BASE DE DATOS, SIEMPRE QUE CUMPLAN CON EL ESTADO PROPORCIONADO.",
            method = "GET",
            parameters = {
                    @Parameter(name = "vaccineId",
                            description = "VALOR NUMÉRICO QUE REPRESENTA EL IDENTIFICADOR DE UNA VACUNA",
                            example = "1")
            }
    )
    @SecurityRequirement(name = "userAuthorization")
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
    @GetMapping("/findAllEmployeesByVaccineType/{vaccineId}")
    public ResponseEntity<Map<String, Object>> findAllEmployeesByVaccineType(
            @PathVariable Long vaccineId) {
        List<Employee> employees;
        Map<String, Object> response = new HashMap<>();
        try {
            employees = iEmployeeService.findAllEmployeesByVaccineType(vaccineId);
        } catch (Exception exception) {
            return iValidationService.employeeValidations(exception);
        }
        if (employees.isEmpty()) {
            response.put(MESSAGE_VALUE, MESSAGE_VALUE_DATA_NOT_FOUND);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            response.put(MESSAGE_VALUE, MESSAGE_VALUE_DATA_FOUND);
            response.put(RESPONSE_VALUE, employees
                    .stream()
                    .map(user -> modelMapper.map(user, EmployeeRegisterDTO.class))
                    .collect(Collectors.toList()));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @Operation(
            summary = "LISTAR EMPLEADOS EN BASE A LA FECHA DE VACUNACIÓN {ROLE ADMIN}",
            description = "ESTE SERVICIO SE ENCARGA DE LISTAR TODOS LOS REGISTROS DE EMPLEADO QUE SE ENCUENTREN " +
                    "EN LA BASE DE DATOS, SIEMPRE QUE CUMPLAN CON EL ESTADO PROPORCIONADO.",
            method = "GET",
            parameters = {
                    @Parameter(name = "datesFilterDTO",
                            description = "OBJETO QUE PERMITE FILTRAR LOS REGISTROS EN BASE DE DATOS POR FECHAS",
                            schema = @Schema(
                                    implementation = DatesFilterDTO.class
                            )
                    )
            }
    )
    @SecurityRequirement(name = "userAuthorization")
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
    @Secured({"ROLE_ADMIN"})
    @GetMapping("/findAllEmployeesByDates")
    public ResponseEntity<Map<String, Object>> findAllEmployeesByDates(
            @Valid @RequestBody DatesFilterDTO datesFilterDTO) {
        List<Employee> employees;
        Map<String, Object> response = new HashMap<>();
        try {
            employees = iEmployeeService.findAllEmployeesByDates(datesFilterDTO.getStartDate(), datesFilterDTO.getEndDate());
        } catch (Exception exception) {
            return iValidationService.employeeValidations(exception);
        }
        if (employees.isEmpty()) {
            response.put(MESSAGE_VALUE, MESSAGE_VALUE_DATA_NOT_FOUND);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            response.put(MESSAGE_VALUE, MESSAGE_VALUE_DATA_FOUND);
            response.put(RESPONSE_VALUE, employees
                    .stream().distinct()
                    .map(user -> modelMapper.map(user, EmployeeRegisterDTO.class))
                    .collect(Collectors.toList()));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }


    @Operation(
            summary = "BUSCAR UN EMPLEADO POR SU NUMERO DE CÉDULA {ROLE ADMIN, ROLE EMPLOYEE}",
            description = "ESTE SERVICIO SE ENCARGA DE BUSCAR UN EMPLEADO POR SU NUMERO DE CÉDULA.",
            parameters = {@Parameter(
                    name = "employeeIdentification",
                    description = "CÉDULA DE IDENTIDAD DE UN EMPLEADO",
                    example = "0106126154",
                    required = true
            )},
            method = "GET"
    )
    @SecurityRequirement(name = "userAuthorization")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "SI SE ENCUENTRAN REGISTROS EN LA BASE DE DATOS",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = Employee.class
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
    @Secured({"ROLE_ADMIN", "ROLE_EMPLOYEE"})
    @GetMapping("/findEmployeeByIdentification/{employeeIdentification}")
    public ResponseEntity<Map<String, Object>> findEmployeeByIdentification(
            @PathVariable String employeeIdentification) {
        Employee employee;
        Map<String, Object> response = new HashMap<>();
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

    @Operation(
            summary = "ELIMINAR REGISTRO DE EMPLEADO DE LA BASE DE DATOS {ROLE ADMIN}",
            description = "ESTE SERVICIO SE ENCARGA DE ELIMINAR LOS REGISTROS DE UN EMPLEADO DENTRO" +
                    "DE LA BASE DE DATOS.",
            parameters = {@Parameter(
                    name = "employeeID",
                    description = "IDENTIFICADOR DEL USUARIO",
                    example = "1",
                    required = true
            )},
            method = "DELETE"
    )
    @SecurityRequirement(name = "userAuthorization")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "SI EL OBJETO ES ELIMINADO CORRECTAMENTE"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "SI EXISTE UN ERROR EN EL SERVIDOR RETORNARA ESTE OBJETO"
                    )
            })
    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/deleteEmployee/{employeeID}")
    public ResponseEntity<Map<String, Object>> findAllEmployeeByIdentification(
            @PathVariable Long employeeID
    ) {
        Map<String, Object> response = new HashMap<>();
        try {
            iEmployeeService.deleteEmployee(employeeID);
        } catch (Exception exception) {
            return iValidationService.employeeValidations(exception);
        }
        response.put(MESSAGE_VALUE, MESSAGE_VALUE_DATA_DELETE);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "REGISTRAR INFORMACIÓN BÁSICA DE USUARIO {ROLE ADMIN, ROLE EMPLOYEE}",
            description = "ESTE SERVICIO SE ENCARGA DE PERSISTIR UN NUEVO EMPLEADO EN LA BASE DE DATOS, SEGÚN EL" +
                    "REQUERIMIENTO SE DEBE PODER REGISTRAR, ACTUALIZAR, LISTAR Y ELIMINAR LOS REGISTROS DE EMPLEADOS",
            parameters = {@Parameter(
                    name = "employeeId",
                    description = "IDENTIFICADOR UNICO DEL EMPLEADO",
                    example = "1",
                    required = true
            ),
                    @Parameter(
                            name = "employeeUpdateDTO",
                            description = "DTO QUE PERMITE ACTUALIZAR LOS DATOS DEL EMPLEADO",
                            schema = @Schema(
                                    implementation = EmployeeUpdateDTO.class
                            ),
                            required = true
                    )},
            method = "PUT"
    )
    @SecurityRequirement(name = "userAuthorization")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "SI EL OBJETO CUMPLE CON LAS VALIDACIONES PREVIAS SERÁ ACTUALIZADO EN LA " +
                                    "BASE DE DATOS.",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = Employee.class
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
    @Secured({"ROLE_ADMIN", "ROLE_EMPLOYEE"})
    @PutMapping("/updateEmployeeData/{employeeId}")
    public ResponseEntity<Map<String, Object>> updateEmployeeData(
            @Valid @RequestBody EmployeeUpdateDTO employeeUpdateDTO,
            @PathVariable("employeeId") Long employeeId,
            BindingResult result
    ) {
        Employee updateEmployee = new Employee();
        Map<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            response.put(MESSAGE_VALUE, MESSAGE_VALUE_BAD_REQUEST);
            response.put(ERROR_VALUE, iValidationService.validationDTO(result.getFieldErrors()));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        Map<String, String> responseAux = updateEmployee.validateEmployeeUpdateDTO(employeeUpdateDTO);
        if (!responseAux.isEmpty()) {
            response.put(MESSAGE_VALUE, MESSAGE_VALUE_BAD_REQUEST);
            response.put(ERROR_VALUE, responseAux);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        Employee currentEmployee = iEmployeeService.findEmployeeById(employeeId);
        if (currentEmployee == null) {
            response.put(MESSAGE_VALUE, MESSAGE_VALUE_BAD_REQUEST);
            response.put(ERROR_VALUE, responseAux);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        updateEmployee = modelMapper.map(employeeUpdateDTO, Employee.class);
        updateEmployee.setEmployeeId(currentEmployee.getEmployeeId());
        updateEmployee.setEmployeeIdentification(currentEmployee.getEmployeeIdentification());
        updateEmployee.setEmployeeEmail(currentEmployee.getEmployeeEmail());

        try {
            updateEmployee = iEmployeeService.saveEmployee(updateEmployee);
        } catch (Exception exception) {
            return iValidationService.employeeValidations(exception);
        }
        response.put(MESSAGE_VALUE, MESSAGE_VALUE_DATA_UPDATE);
        response.put(RESPONSE_VALUE, updateEmployee);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
