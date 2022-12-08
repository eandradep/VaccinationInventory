package com.kruger.vaccination.inventory.models.services.validation;

import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;

public interface IValidationService {

    Map<String, String> validationDTO(List<FieldError> result);

    Map<String, String> employeeValidations();
}
