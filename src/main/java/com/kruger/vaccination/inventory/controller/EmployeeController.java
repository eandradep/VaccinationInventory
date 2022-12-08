package com.kruger.vaccination.inventory.controller;

import com.kruger.vaccination.inventory.models.dto.EmployeeRegisterDTO;
import com.kruger.vaccination.inventory.models.entity.Employee;
import com.kruger.vaccination.inventory.models.services.employee.IEmployeeService;
import com.kruger.vaccination.inventory.models.services.validation.IValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kruger.vaccination.inventory.configuration.StaticValues.*;

@RestController
@RequestMapping("/employeeController")
public class EmployeeController {

    private final Map<String, Object> response = new HashMap<>();
    @Autowired
    private IEmployeeService iEmployeeService;
    @Autowired
    private IValidationService iValidationService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/saveEmployee")
    public ResponseEntity<Map<String, Object>> saveEmployee(
            @Valid @RequestBody EmployeeRegisterDTO employeeRegisterDTO, BindingResult result
    ) {
        Employee employeeToPersist = new Employee();
        if (result.hasErrors()) {
            response.put(MESSAGE_VALUE, MESSAGE_VALUE_BAD_REQUEST);
            response.put(ERROR_VALUE, iValidationService.validationDTO(result.getFieldErrors()));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (!employeeToPersist.validateEmployeeDTO(employeeRegisterDTO)) {
            response.put(MESSAGE_VALUE, MESSAGE_VALUE_BAD_REQUEST);
            response.put(ERROR_VALUE, iValidationService.employeeValidations());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        Employee newEmployee;
        employeeToPersist = modelMapper.map(employeeRegisterDTO, Employee.class);
        try {
            newEmployee = iEmployeeService.saveEmployee(employeeToPersist);
        } catch (DataAccessException dataAccessException) {
            response.put(MESSAGE_VALUE, MESSAGE_VALUE_INTERNAL_SERVER_ERROR);
            response.put(ERROR_VALUE,
                    dataAccessException.getMessage() + " : " + dataAccessException.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put(MESSAGE_VALUE, MESSAGE_VALUE_DATA_SAVE);
        response.put(RESPONSE_VALUE, newEmployee);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/findAllEmployees")
    public ResponseEntity<Map<String, Object>> findAllEmployees() {
        List<Employee> employees;
        try {
            employees = iEmployeeService.findAllEmployees();
        } catch (DataAccessException dataAccessException) {
            response.put(MESSAGE_VALUE, MESSAGE_VALUE_INTERNAL_SERVER_ERROR);
            response.put(ERROR_VALUE,
                    dataAccessException.getMessage() + " : " + dataAccessException.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
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

}
