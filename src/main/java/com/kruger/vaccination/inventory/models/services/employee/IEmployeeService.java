package com.kruger.vaccination.inventory.models.services.employee;

import com.kruger.vaccination.inventory.models.entity.Employee;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IEmployeeService {

    /***
     * Find All Employees
     * @return retorna el listado de todos los empleados registrados en la base de datos.
     * **/
    @Transactional(readOnly = true)
    List<Employee> findAllEmployees();

    /***
     * Find Employee by Identification
     * @param employeeIdentification cédula del empleado, con este valor String se procede a realizar la búsqueda
     *                               en base de datos.
     * @return si la cédula del empleado proporcionada coincide con las cédulas registradas en la base de datos,
     * retornará un empleado con todos sus datos, caso contrario la respuesta es null.
     * **/
    @Transactional(readOnly = true)
    Employee findEmployeeByIdentification(String employeeIdentification);

    /***
     * Save Employee
     * @param employee entidad de empleado que se va a almacenar en la base de datos, si la cédula del usuario ya
     *                 existe en la base de datos, el registro se actualizará, caso contrario se creara uno nuevo.
     * @return retorna el objeto almacenado en base de datos.
     * **/
    @Transactional()
    Employee saveEmployee(Employee employee);

}
