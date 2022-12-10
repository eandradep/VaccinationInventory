package com.kruger.vaccination.inventory.models.dao;

import com.kruger.vaccination.inventory.models.entity.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IEmployeeDAO extends CrudRepository<Employee, Long> {

    Optional<Employee> findEmployeeByEmployeeIdentification(String employeeIdentification);

    List<Employee> findEmployeeByEmployeeIsVaccinate(Boolean employeeIsVaccinate);

    List<Employee> findEmployeesByImmunizationRecordListVaccineVaccineId(Long vaccineID);

    List<Employee>findEmployeeByImmunizationRecordListVaccinationDateBetween(Date startDate, Date endDate);

}
