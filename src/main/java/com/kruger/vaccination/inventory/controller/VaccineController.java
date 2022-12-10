package com.kruger.vaccination.inventory.controller;

import com.kruger.vaccination.inventory.models.dto.VaccineRegisterDTO;
import com.kruger.vaccination.inventory.models.entity.Vaccine;
import com.kruger.vaccination.inventory.models.services.vaccinetype.IVaccineService;
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
        name = "CONTROLADOR DE VACUNAS",
        description = "ESTE CONTROLADOR SE ENCARGA DE ADMINISTRAR EL CRUD DE LA TABLA VACUNAS, SEGÚN EL ROL DEL" +
                "USUARIO, SE PERMITIRÁ LA POSIBILIDAD DE CREAR, ACTUALIZAR O LISTAR LA INFORMACIÓN DE LAS VACUNAS" +
                "EN BASE A LOS REQUERIMIENTOS DEL CLIENTE."
)
@RestController
@RequestMapping("/vaccineController")
public class VaccineController {

    @Autowired
    private IVaccineService iVaccineService;
    @Autowired
    private IValidationService iValidationService;
    @Autowired
    private ModelMapper modelMapper;

    @Operation(
            summary = "REGISTRAR UNA NUEVA VACUNA",
            description = "ESTE SERVICIO SE ENCARGA DE REGISTRAR UN NUEVO TIPO DE VACUNA EN LA BASE DE DATOS, " +
                    "ÚNICAMENTE SE REQUIERE EL NOMBRE DE LA VACUNA PARA PODER REALIZAR EL REGISTRO.",
            parameters = {@Parameter(
                    name = "vaccineRegisterDTO",
                    description = "DTO USADO PARA PODER PERSISTIR UNA ENTIDAD EN LA BASE DE DATOS.",
                    schema = @Schema(
                            implementation = VaccineRegisterDTO.class
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
                                                    implementation = Vaccine.class
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
    @PostMapping("/saveVaccine")
    public ResponseEntity<Map<String, Object>> saveVaccine(
            @Valid @RequestBody VaccineRegisterDTO vaccineRegisterDTO,
            BindingResult result
    ) {
        Vaccine vaccine;
        Map<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            response.put(MESSAGE_VALUE, MESSAGE_VALUE_BAD_REQUEST);
            response.put(ERROR_VALUE, iValidationService.validationDTO(result.getFieldErrors()));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        Vaccine newVaccine;
        vaccine = modelMapper.map(vaccineRegisterDTO, Vaccine.class);
        try {
            newVaccine = iVaccineService.saveVaccine(vaccine);
        } catch (Exception exception) {
            return iValidationService.employeeValidations(exception);
        }
        response.put(MESSAGE_VALUE, MESSAGE_VALUE_DATA_SAVE);
        response.put(RESPONSE_VALUE, newVaccine);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "LISTAR TODOS LOS EMPLEADOS",
            description = "ESTE SERVICIO SE ENCARGA DE LISTAR TODOS LOS REGISTROS DE VACUNAS QUE SE ENCUENTREN " +
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
                                                    allOf = Vaccine.class
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
    @GetMapping("/findAllVaccines")
    public ResponseEntity<Map<String, Object>> findAllVaccines() {
        List<Vaccine> vaccineList;
        Map<String, Object> response = new HashMap<>();
        try {
            vaccineList = iVaccineService.findAll();
        } catch (Exception exception) {
            return iValidationService.employeeValidations(exception);
        }
        if (vaccineList.isEmpty()) {
            response.put(MESSAGE_VALUE, MESSAGE_VALUE_DATA_NOT_FOUND);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            response.put(MESSAGE_VALUE, MESSAGE_VALUE_DATA_FOUND);
            response.put(RESPONSE_VALUE, vaccineList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }


}
