package com.sprk.employeeproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor

public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int empId;

    @Column(nullable = false)
    @NotBlank(message = "Please enter first name")
    private String firstName;

    @Column(nullable = false)
    @NotBlank(message = "Please enter last name")
    private String lastName;

    @Column(unique = true, nullable = false)
    @Email(message = "Please enter correct email")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Phone number cannot be empty")
    @Pattern(regexp = "(\\+[0-9]{1,3})\\s[0-9]{10}", message = "Phone number must contain only numbers")
    private String phoneNo;

    public Employee(Employee employee) {
        this.empId = employee.empId;
        this.firstName = employee.firstName;
        this.lastName = employee.lastName;
        this.email = employee.email;
        this.phoneNo = employee.phoneNo;
    }

}
