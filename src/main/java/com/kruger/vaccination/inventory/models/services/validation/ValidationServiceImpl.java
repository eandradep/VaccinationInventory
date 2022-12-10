package com.kruger.vaccination.inventory.models.services.validation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kruger.vaccination.inventory.configuration.StaticValues.*;

@Service
public class ValidationServiceImpl implements IValidationService {


    private Map<String, String> errorList;

    @Override
    public Map<String, String> validationDTO(List<FieldError> result) {
        errorList = new HashMap<>();
        result.forEach(
                fieldError -> errorList.put(fieldError.getField(), fieldError.getDefaultMessage())
        );
        return errorList;
    }

    @Override
    public ResponseEntity<Map<String, Object>> employeeValidations(Exception exception) {
        exception.printStackTrace();
        Map<String, Object> response = new HashMap<>();
        errorList = new HashMap<>();
        if (exception.getCause().toString().contains("ConstraintViolationException")){
            response.put(MESSAGE_VALUE, MESSAGE_VALUE_INTERNAL_DUPLICATE_KEY);
            response.put(ERROR_VALUE, exception.getMessage() + " : " + exception.getCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } else {
            response.put(MESSAGE_VALUE, MESSAGE_VALUE_INTERNAL_SERVER_ERROR);
            response.put(ERROR_VALUE, exception.getMessage() + " : " + exception.getCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
