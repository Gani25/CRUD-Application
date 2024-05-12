package com.sprk.employeeproject.service;

import java.util.List;
import java.util.Optional;

import com.sprk.employeeproject.entity.Employee;

public interface EmployeeService {

    Employee saveEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Optional<Employee> getEmployeeById(int empId);

    void deleteEmployee(Employee employee);

    List<Employee> getEmployeeByEmail(String email);

    void removeSessionMsg();

    void removeSessionDeletedMsg();
}
