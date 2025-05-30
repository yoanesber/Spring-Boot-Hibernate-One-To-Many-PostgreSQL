package com.yoanesber.spring.hibernate.one_to_many_postgresql.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import com.yoanesber.spring.hibernate.one_to_many_postgresql.dto.EmployeeDTO;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.dto.HttpResponseDTO;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.Employee;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.mapper.EmployeeMapper;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.service.EmployeeService;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.util.ResponseUtil;

/**
 * EmployeeController handles HTTP requests related to employees.
 * It provides endpoints to create, retrieve, update, and delete employees.
 */

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    private static final String INVALID_REQUEST = "Invalid Request";
    private static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    private static final String RECORD_NOT_FOUND = "Record not found";
    private static final String RECORD_RETRIEVED_SUCCESSFULLY = "Record retrieved successfully";
    private static final String RECORD_CREATED_SUCCESSFULLY = "Record created successfully";
    private static final String RECORD_UPDATED_SUCCESSFULLY = "Record updated successfully";
    private static final String RECORD_DELETED_SUCCESSFULLY = "Record deleted successfully";

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<HttpResponseDTO> saveEmployee(@RequestBody EmployeeDTO employeeDTO,
        HttpServletRequest request) {
        try {
            if (employeeDTO == null) {
                return ResponseUtil.buildBadRequestResponse(request, 
                    INVALID_REQUEST,
                    "Employee body request cannot be null",
                    null);
            }

            Employee createdEmployee = employeeService
                .saveEmployee(EmployeeMapper.toEntity(employeeDTO));
            if (createdEmployee == null) {
                return ResponseUtil.buildInternalServerErrorResponse(request, 
                    INTERNAL_SERVER_ERROR,
                    "Failed to create employee",
                    null);
            }

            return ResponseUtil.buildCreatedResponse(request, 
                RECORD_CREATED_SUCCESSFULLY,
                EmployeeMapper.toDTO(createdEmployee));
        } catch (EntityExistsException e) {
            return ResponseUtil.buildBadRequestResponse(request, 
                INVALID_REQUEST,
                e.getMessage(),
                null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.buildNotFoundResponse(request, 
                RECORD_NOT_FOUND,
                e.getMessage(),
                null);
        } catch (Exception e) {
            return ResponseUtil.buildInternalServerErrorResponse(request, 
                INTERNAL_SERVER_ERROR,
                "An error occurred while saving the employee: " + e.getMessage(),
                null);
        }
    }

    @GetMapping
    public ResponseEntity<HttpResponseDTO> getAllEmployees(HttpServletRequest request) {
        try {
            List<Employee> employees = employeeService.getAllEmployees();
            if (employees == null || employees.isEmpty()) {
                return ResponseUtil.buildNotFoundResponse(request, 
                    RECORD_NOT_FOUND, 
                    "No employees found",
                    null);
            }

            return ResponseUtil.buildOkResponse(request, 
                RECORD_RETRIEVED_SUCCESSFULLY,
                EmployeeMapper.toDTOList(employees));
        } catch (Exception e) {
            return ResponseUtil.buildInternalServerErrorResponse(request, 
                INTERNAL_SERVER_ERROR,
                "An error occurred while fetching employees: " + e.getMessage(),
                null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponseDTO> getEmployeeById(@PathVariable Long id,
        HttpServletRequest request) {
        try {
            if (id == null) {
                return ResponseUtil.buildBadRequestResponse(request, 
                    INVALID_REQUEST,
                    "Employee id cannot be null",
                    null);
            } 
            
            Employee employee = employeeService.getEmployeeById(id);
            if (employee == null) {
                return ResponseUtil.buildNotFoundResponse(request, 
                    RECORD_NOT_FOUND, 
                    "Employee not found with id: " + id,
                    null);
            }

            return ResponseUtil.buildOkResponse(request, 
                RECORD_RETRIEVED_SUCCESSFULLY,
                EmployeeMapper.toDTO(employee));
        } catch (Exception e) {
            return ResponseUtil.buildInternalServerErrorResponse(request, 
                INTERNAL_SERVER_ERROR,
                "An error occurred while fetching the employee: " + e.getMessage(),
                null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpResponseDTO> updateEmployee(@PathVariable Long id, 
        @RequestBody EmployeeDTO employeeDTO, HttpServletRequest request) {
        try {
            if (id == null) {
                return ResponseUtil.buildBadRequestResponse(request, 
                    INVALID_REQUEST,
                    "Employee id cannot be null",
                    null);
            }

            if (employeeDTO == null) {
                return ResponseUtil.buildBadRequestResponse(request, 
                    INVALID_REQUEST,
                    "Employee body request cannot be null",
                    null);
            }

            Employee updatedEmployee = employeeService
                .updateEmployee(id, EmployeeMapper.toEntity(employeeDTO));
            if (updatedEmployee == null) {
                return ResponseUtil.buildNotFoundResponse(request, 
                    RECORD_NOT_FOUND, 
                    "Employee not found with id: " + id,
                    null);
            }
            
            return ResponseUtil.buildOkResponse(request, 
                RECORD_UPDATED_SUCCESSFULLY,
                EmployeeMapper.toDTO(updatedEmployee));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.buildNotFoundResponse(request, 
                RECORD_NOT_FOUND,
                e.getMessage(),
                null);
        } catch (Exception e) {
            return ResponseUtil.buildInternalServerErrorResponse(request, 
                INTERNAL_SERVER_ERROR,
                "An error occurred while updating the employee: " + e.getMessage(),
                null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpResponseDTO> deleteEmployee(@PathVariable Long id,
        HttpServletRequest request) {
        try {
            if (id == null) {
                return ResponseUtil.buildBadRequestResponse(request, 
                    INVALID_REQUEST,
                    "Employee id cannot be null",
                    null);
            }
            
            if (!employeeService.deleteEmployee(id)) {
                return ResponseUtil.buildNotFoundResponse(request, 
                    RECORD_NOT_FOUND, 
                    "Employee not found with id: " + id,
                    null);
            }

            return ResponseUtil.buildOkResponse(request, 
                RECORD_DELETED_SUCCESSFULLY,
                null);
        } catch (Exception e) {
            return ResponseUtil.buildInternalServerErrorResponse(request, 
                INTERNAL_SERVER_ERROR,
                "An error occurred while deleting the employee: " + e.getMessage(),
                null);
        }
    }
}
