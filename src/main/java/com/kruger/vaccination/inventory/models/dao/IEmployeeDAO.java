package com.kruger.vaccination.inventory.models.dao;

import com.kruger.vaccination.inventory.models.entity.Employee;
import org.springframework.data.repository.CrudRepository;

public interface IEmployeeDAO extends CrudRepository<Employee, String> {
}
