package com.kruger.vaccination.inventory.models.dao;

import com.kruger.vaccination.inventory.models.entity.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IEmployeeDAO extends CrudRepository<Employee, Long> {

    Optional<Employee> findEmployeeByEmployeeIdentification(String employeeIdentification);
}
