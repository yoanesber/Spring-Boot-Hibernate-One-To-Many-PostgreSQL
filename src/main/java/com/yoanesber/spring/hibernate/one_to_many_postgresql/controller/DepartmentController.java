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

import com.yoanesber.spring.hibernate.one_to_many_postgresql.dto.DepartmentDTO;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.dto.HttpResponseDTO;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.Department;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.mapper.DepartmentMapper;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.service.DepartmentService;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.util.ResponseUtil;

/**
 * DepartmentController handles HTTP requests related to departments.
 * It provides endpoints to create, retrieve, update, and delete departments.
 */

@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    private static final String INVALID_REQUEST = "Invalid Request";
    private static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    private static final String RECORD_NOT_FOUND = "Record not found";
    private static final String RECORD_RETRIEVED_SUCCESSFULLY = "Record retrieved successfully";
    private static final String RECORD_CREATED_SUCCESSFULLY = "Record created successfully";
    private static final String RECORD_UPDATED_SUCCESSFULLY = "Record updated successfully";
    private static final String RECORD_DELETED_SUCCESSFULLY = "Record deleted successfully";

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<HttpResponseDTO> saveDepartment(@RequestBody DepartmentDTO departmentDTO,
        HttpServletRequest request) {
        if (departmentDTO == null) {
            return ResponseUtil.buildBadRequestResponse(request, 
                INVALID_REQUEST,
                "Department body request cannot be null",
                null);
        }

        if (departmentDTO.getDeptName() == null || departmentDTO.getDeptName().isEmpty()) {
            return ResponseUtil.buildBadRequestResponse(request, 
                INVALID_REQUEST,
                "Department name cannot be null or empty",
                null);
        }
        
        try {
            Department createdDepartment = departmentService
                .saveDepartment(DepartmentMapper.toEntity(departmentDTO));
            if (createdDepartment == null) {
                return ResponseUtil.buildInternalServerErrorResponse(request, 
                    INTERNAL_SERVER_ERROR,
                    "Failed to create department",
                    null);
            }

            return ResponseUtil.buildCreatedResponse(request, 
                RECORD_CREATED_SUCCESSFULLY,
                DepartmentMapper.toDTO(createdDepartment));
        } catch (Exception e) {
            return ResponseUtil.buildInternalServerErrorResponse(request, 
                INTERNAL_SERVER_ERROR,
                "An error occurred while saving the department: " + e.getMessage(),
                null);
        }
    }

    @GetMapping
    public ResponseEntity<HttpResponseDTO> getAllDepartments(HttpServletRequest request) {
        try {
            List<Department> departments = departmentService.getAllDepartments();
            if (departments == null || departments.isEmpty()) {
                return ResponseUtil.buildNotFoundResponse(request, 
                    RECORD_NOT_FOUND, 
                    "No departments found",
                    null);
            }
            
            return ResponseUtil.buildOkResponse(request, 
                RECORD_RETRIEVED_SUCCESSFULLY,
                DepartmentMapper.toDTOList(departments));
        } catch (Exception e) {
            return ResponseUtil.buildInternalServerErrorResponse(request, 
                INTERNAL_SERVER_ERROR,
                "An error occurred while fetching departments: " + e.getMessage(),
                null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponseDTO> getDepartmentById(@PathVariable String id,
        HttpServletRequest request) {
        if (id == null || id.isEmpty()) {
            return ResponseUtil.buildBadRequestResponse(request, 
                INVALID_REQUEST,
                "Department id cannot be null or empty",
                null);
        }

        try {
            id = id.toLowerCase();
            Department department = departmentService.getDepartmentById(id);
            if (department == null) {
                return ResponseUtil.buildNotFoundResponse(request, 
                    RECORD_NOT_FOUND, 
                    "Department not found with id: " + id,
                    null);
            }

            return ResponseUtil.buildOkResponse(request, 
                RECORD_RETRIEVED_SUCCESSFULLY,
                DepartmentMapper.toDTO(department));
        } catch (Exception e) {
            return ResponseUtil.buildInternalServerErrorResponse(request, 
                INTERNAL_SERVER_ERROR,
                "An error occurred while fetching the department: " + e.getMessage(),
                null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpResponseDTO> updateDepartment(@PathVariable String id, 
        @RequestBody DepartmentDTO departmentDTO, HttpServletRequest request) {
        if (id == null || id.isEmpty()) {
            return ResponseUtil.buildBadRequestResponse(request, 
                INVALID_REQUEST,
                "Department id cannot be null or empty",
                null);
        }

        if (departmentDTO == null) {
            return ResponseUtil.buildBadRequestResponse(request, 
                INVALID_REQUEST,
                "Department body request cannot be null",
                null);
        }
        
        try {
            id = id.toLowerCase();
            Department updatedDepartment = departmentService
                .updateDepartment(id, DepartmentMapper.toEntity(departmentDTO));
            if (updatedDepartment == null) {
                return ResponseUtil.buildNotFoundResponse(request, 
                    RECORD_NOT_FOUND, 
                    "Department not found with id: " + id,
                    null);
            }

            return ResponseUtil.buildOkResponse(request, 
                RECORD_UPDATED_SUCCESSFULLY,
                DepartmentMapper.toDTO(updatedDepartment));
        } catch (Exception e) {
            return ResponseUtil.buildInternalServerErrorResponse(request, 
                INTERNAL_SERVER_ERROR,
                "An error occurred while updating the department: " + e.getMessage(),
                null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpResponseDTO> deleteDepartment(@PathVariable String id,
        HttpServletRequest request) {
        if (id == null || id.isEmpty()) {
            return ResponseUtil.buildBadRequestResponse(request, 
                INVALID_REQUEST,
                "Department id cannot be null or empty",
                null);
        }

        try {
            id = id.toLowerCase();
            if (!departmentService.deleteDepartment(id)) {
                return ResponseUtil.buildNotFoundResponse(request, 
                    RECORD_NOT_FOUND, 
                    "Department not found with id: " + id,
                    null);
            }

            return ResponseUtil.buildOkResponse(request, 
                RECORD_DELETED_SUCCESSFULLY,
                null);
        } catch (Exception e) {
            return ResponseUtil.buildInternalServerErrorResponse(request, 
                INTERNAL_SERVER_ERROR,
                "An error occurred while deleting the department: " + e.getMessage(),
                null);
        }
    }
}
