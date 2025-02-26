package com.yoanesber.spring.hibernate.one_to_many_postgresql.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yoanesber.spring.hibernate.one_to_many_postgresql.dto.EmployeeDTO;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.CustomHttpResponse;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.service.EmployeeService;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Object> saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try {
            if (employeeDTO == null) {
                return ResponseEntity.badRequest().body(new CustomHttpResponse(HttpStatus.BAD_REQUEST.value(), 
                    "Employee cannot be null", null));
            }

            employeeService.saveEmployee(employeeDTO);

            return ResponseEntity.created(null).body(new CustomHttpResponse(HttpStatus.CREATED.value(), 
                "Employee saved successfully", null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new CustomHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAllEmployees() {
        try {
            List<EmployeeDTO> employees = employeeService.getAllEmployees();

            if (employees.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomHttpResponse(HttpStatus.NOT_FOUND.value(), 
                    "No employees found", null));
            }

            return ResponseEntity.ok(new CustomHttpResponse(HttpStatus.OK.value(), 
                "All employees fetched successfully", employees));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new CustomHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEmployeeById(@PathVariable Long id) {
        try {
            if (id == null) {
                return ResponseEntity.badRequest().body(new CustomHttpResponse(HttpStatus.BAD_REQUEST.value(), 
                    "Employee id cannot be null", null));
            } 
            
            EmployeeDTO employee = employeeService.getEmployeeById(id);

            if (employee == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomHttpResponse(HttpStatus.NOT_FOUND.value(), 
                    "Employee not found", null));
            }

            return ResponseEntity.ok(new CustomHttpResponse(HttpStatus.OK.value(), 
                "Employee fetched successfully", employee));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new CustomHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        try {
            if (id == null) {
                return ResponseEntity.badRequest().body(new CustomHttpResponse(HttpStatus.BAD_REQUEST.value(), 
                    "Employee id cannot be null", null));
            }

            if (employeeDTO == null) {
                return ResponseEntity.badRequest().body(new CustomHttpResponse(HttpStatus.BAD_REQUEST.value(), 
                    "Employee cannot be null", null));
            }

            EmployeeDTO updatedEmployee = employeeService.updateEmployee(id, employeeDTO);
            
            return ResponseEntity.ok(new CustomHttpResponse(HttpStatus.OK.value(), 
                "Employee updated successfully", updatedEmployee));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new CustomHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable Long id) {
        try {
            if (id == null) {
                return ResponseEntity.badRequest().body(new CustomHttpResponse(HttpStatus.BAD_REQUEST.value(), 
                    "Employee id cannot be null", null));
            }

            employeeService.deleteEmployee(id);

            return ResponseEntity.ok(new CustomHttpResponse(HttpStatus.OK.value(), 
                "Employee deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new CustomHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                e.getMessage(), null));
        }
    }
}
