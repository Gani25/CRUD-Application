package com.sprk.employeeproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sprk.employeeproject.entity.Employee;
import com.sprk.employeeproject.repository.EmployeeRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    // private HttpServletRequest request;

    // public EmployeeServiceImpl(EmployeeRepository employeeRepository,
    // HttpServletRequest request) {
    // this.employeeRepository = employeeRepository;
    // this.request = request;
    // }

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;

    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);

    }

    @Override
    public List<Employee> getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    @Override
    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(int empId) {
        Optional<Employee> employee = employeeRepository.findById(empId);
        return employee;
    }

    @Override
    public void deleteEmployee(Employee employee) {
        employeeRepository.delete(employee);

    }

    @Override
    public void removeSessionMsg() {

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        HttpServletRequest request = attr.getRequest();
        HttpSession session = request.getSession();

        session.removeAttribute("msg");
    }

    @Override
    public void removeSessionDeletedMsg() {

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        HttpServletRequest request = attr.getRequest();
        HttpSession session = request.getSession();

        session.removeAttribute("deleteMsg");
    }
}
