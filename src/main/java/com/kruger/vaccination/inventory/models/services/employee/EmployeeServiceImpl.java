package com.kruger.vaccination.inventory.models.services.employee;

import com.kruger.vaccination.inventory.models.dao.IEmployeeDAO;
import com.kruger.vaccination.inventory.models.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return iEmployeeDAO.findById(employeeIdentification).orElse(null);
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return iEmployeeDAO.save(employee);
    }
}
