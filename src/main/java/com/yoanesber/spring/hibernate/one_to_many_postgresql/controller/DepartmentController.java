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

import com.yoanesber.spring.hibernate.one_to_many_postgresql.dto.DepartmentDTO;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.CustomHttpResponse;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.Department;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.service.DepartmentService;

@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<Object> saveDepartment(@RequestBody DepartmentDTO departmentDTO) {
        try {
            // Check if the input is null
            if (departmentDTO == null) {
                return ResponseEntity.badRequest().body(new CustomHttpResponse(HttpStatus.BAD_REQUEST.value(), 
                    "Department cannot be null", null));
            } 
            
            // Save department
            departmentService.saveDepartment(departmentDTO);

            // Return the response
            return ResponseEntity.created(null).body(new CustomHttpResponse(HttpStatus.CREATED.value(), 
                "Department saved successfully", null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new CustomHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAllDepartments() {
        try {
            // Get all departments
            List<DepartmentDTO> departments = departmentService.getAllDepartments();

            // Check if the list is empty
            if (departments == null || departments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomHttpResponse(HttpStatus.NOT_FOUND.value(), 
                    "No departments found", null));
            }
            
            // Return the response
            return ResponseEntity.ok(new CustomHttpResponse(HttpStatus.OK.value(), 
                "All departments fetched successfully", departments));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new CustomHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDepartmentById(@PathVariable String id) {
        try {
            // Check if the input is null
            if (id == null || id.isEmpty()) {
                return ResponseEntity.badRequest().body(new CustomHttpResponse(HttpStatus.BAD_REQUEST.value(), 
                    "Department id cannot be null or empty", null));
            }

            // Get department by id
            id = id.toLowerCase();
            Department department = departmentService.getDepartmentById(id);

            // Create department DTO
            DepartmentDTO departmentDto = new DepartmentDTO(department);

            // Check if department is null
            if (department == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomHttpResponse(HttpStatus.NOT_FOUND.value(), 
                    "Department not found", null));
            }

            // Return the response
            return ResponseEntity.ok(new CustomHttpResponse(HttpStatus.OK.value(), 
                "Department fetched successfully", departmentDto));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new CustomHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDepartment(@PathVariable String id, @RequestBody DepartmentDTO departmentDTO) {
        try {
            // Check if the input is null
            if (id == null || id.isEmpty()) {
                return ResponseEntity.badRequest().body(new CustomHttpResponse(HttpStatus.BAD_REQUEST.value(), 
                    "Department id cannot be null or empty", null));
            }

            if (departmentDTO == null) {
                return ResponseEntity.badRequest().body(new CustomHttpResponse(HttpStatus.BAD_REQUEST.value(), 
                    "Department cannot be null", null));
            }
            
            // Update department
            id = id.toLowerCase();
            DepartmentDTO updatedDepartment = departmentService.updateDepartment(id, departmentDTO);

            // Check if department is null
            if (updatedDepartment == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomHttpResponse(HttpStatus.NOT_FOUND.value(), 
                    "Department not found", null));
            }

            // Return the response
            return ResponseEntity.ok(new CustomHttpResponse(HttpStatus.OK.value(), 
                "Department updated successfully", updatedDepartment));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new CustomHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDepartment(@PathVariable String id) {
        try {
            // Check if the input is null
            if (id == null || id.isEmpty()) {
                return ResponseEntity.badRequest().body(new CustomHttpResponse(HttpStatus.BAD_REQUEST.value(), 
                    "Department id cannot be null or empty", null));
            }

            // Delete department
            id = id.toLowerCase();
            if (!departmentService.deleteDepartment(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomHttpResponse(HttpStatus.NOT_FOUND.value(), 
                    "Department not found", null));
            }

            // Return the response
            return ResponseEntity.ok(new CustomHttpResponse(HttpStatus.OK.value(), 
                "Department deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new CustomHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                e.getMessage(), null));
        }
    }
}
