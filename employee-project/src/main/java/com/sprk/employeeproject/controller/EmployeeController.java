package com.sprk.employeeproject.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sprk.employeeproject.entity.Employee;
import com.sprk.employeeproject.service.EmployeeService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
// @RequestMapping("/employee")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee/form")
    public String showEmployeeForm(Model model) {
        model.addAttribute("employeeObj", new Employee());
        return "webpage/employee-form";
    }

    @PostMapping("/employee/submit")
    public String processForm(@Valid @ModelAttribute("employeeObj") Employee employee, BindingResult bindingResult,
            HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "webpage/employee-form";
        } else {
            // System.out.println(employee);
            List<Employee> checkEmailEmp = employeeService.getEmployeeByEmail(employee.getEmail());
            System.out.println(checkEmailEmp);
            if (employee.getEmpId() == 0 && !checkEmailEmp.isEmpty()) {
                session.setAttribute("msg", "Employee with email = " + employee.getEmail() + " already exists!! ");
                return "webpage/employee-form";
            }

            Employee beforeInsert = new Employee(employee);
            Employee savedEmployee = employeeService.saveEmployee(employee);

            if (beforeInsert.getEmpId() != 0) {
                session.setAttribute("msg", "Employee with empId = " + employee.getEmpId() + " updated successfully..");

            } else if (savedEmployee != null) {
                session.setAttribute("msg", "Employee saved successfully..");
            } else {
                session.setAttribute("msg", "SOmething went wrong..");
            }

            return "redirect:/employee/dashboard";
        }

    }

    @GetMapping("/employee/dashboard")
    public String showDashboard(Model model) {

        List<Employee> allEmployees = employeeService.getAllEmployees();

        model.addAttribute("employees", allEmployees);

        return "webpage/dashboard";
    }

    @GetMapping("/employee/delete/{empId}")
    public String deleteEmployee(@PathVariable int empId, HttpSession session) {

        Optional<Employee> dbEmployee = employeeService.getEmployeeById(empId);

        if (dbEmployee.isEmpty()) {
            session.setAttribute("deleteMsg", "Employee with " + empId + " doesnot exists in our records!!");
        } else {
            employeeService.deleteEmployee(dbEmployee.get());
            session.setAttribute("deleteMsg", "Employee with " + empId + " deleted from our records!!");

        }
        return "redirect:/employee/dashboard";
    }

    @GetMapping("/employee/update/{empId}")
    public String updateEmployee(@PathVariable int empId, HttpSession session, Model model) {
        Optional<Employee> dbEmployee = employeeService.getEmployeeById(empId);

        if (dbEmployee.isEmpty()) {
            session.setAttribute("deleteMsg", "Employee with " + empId + " doesnot exists in our records!!");
            return "redirect:/employee/dashboard";
        } else {
            model.addAttribute("employeeObj", dbEmployee.get());
            return "webpage/employee-form";
        }

    }

}
