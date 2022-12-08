package com.kruger.vaccination.inventory.models.services.validation;

import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ValidationServiceImpl implements IValidationService {


    private final Map<String, String> errorList = new HashMap<>();

    @Override
    public Map<String, String> validationDTO(List<FieldError> result) {
        result.forEach(
                fieldError -> errorList.put(fieldError.getField(), fieldError.getDefaultMessage())
        );
        return errorList;
    }

    @Override
    public Map<String, String> employeeValidations() {
        errorList.put("firstPossibility", "La cédula contiene valores no numéricos");
        errorList.put("secondPossibility", "La cédula es invalida");
        errorList.put("thirdPossibility", "El nombre de usuario contiene números o caracteres especiales");
        errorList.put("fourthPossibility", "El apellido de usuario contiene números o caracteres especiales");
        return errorList;
    }
}
