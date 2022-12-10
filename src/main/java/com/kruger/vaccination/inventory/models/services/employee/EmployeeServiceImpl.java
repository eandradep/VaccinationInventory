package com.kruger.vaccination.inventory.models.services.employee;

import com.kruger.vaccination.inventory.models.dao.IEmployeeDAO;
import com.kruger.vaccination.inventory.models.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private IEmployeeDAO iEmployeeDAO;

    @Override
    public List<Employee> findAllEmployees() {
        return (List<Employee>) iEmployeeDAO.findAll();
    }

    @Override
    public Employee findEmployeeByIdentification(String employeeIdentification) {
        return iEmployeeDAO.findEmployeeByEmployeeIdentification(employeeIdentification).orElse(null);
    }

    @Override
    public List<Employee> findAllEmployeesByVaccineStatus(Boolean vaccineStatus) {
        return iEmployeeDAO.findEmployeeByEmployeeIsVaccinate(vaccineStatus);
    }

    @Override
    public List<Employee> findAllEmployeesByVaccineType(Long vaccineID) {
        return iEmployeeDAO.findEmployeesByImmunizationRecordListVaccineVaccineId(vaccineID);
    }

    @Override
    public List<Employee> findAllEmployeesByDates(Date startDate, Date endDate) {
        return iEmployeeDAO.findEmployeeByImmunizationRecordListVaccinationDateBetween(startDate, endDate);
    }

    @Override
    public Employee findEmployeeById(Long employeeId) {
        return iEmployeeDAO.findById(employeeId).orElse(null);
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return iEmployeeDAO.save(employee);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        iEmployeeDAO.deleteById(employeeId);
    }
}
