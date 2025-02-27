package com.yoanesber.spring.hibernate.one_to_many_postgresql.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.yoanesber.spring.hibernate.one_to_many_postgresql.dto.EmployeeDTO;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.Department;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.DepartmentEmployee;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.Employee;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.SalaryEmployee;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.entity.TitleEmployee;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.repository.EmployeeRepository;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.service.DepartmentService;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.service.DepartmentEmployeeService;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.service.EmployeeService;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.service.SalaryEmployeeService;
import com.yoanesber.spring.hibernate.one_to_many_postgresql.service.TitleEmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final DepartmentService departmentService;

    private final DepartmentEmployeeService departmentEmployeeService;

    private final SalaryEmployeeService salaryEmployeeService;

    private final TitleEmployeeService titleEmployeeService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
        DepartmentService departmentService, DepartmentEmployeeService departmentEmployeeService,
        SalaryEmployeeService salaryEmployeeService, TitleEmployeeService titleEmployeeService) {
        this.employeeRepository = employeeRepository;
        this.departmentService = departmentService;
        this.departmentEmployeeService = departmentEmployeeService;
        this.salaryEmployeeService = salaryEmployeeService;
        this.titleEmployeeService = titleEmployeeService;
    }

    @Override
    @Transactional
    public void saveEmployee(EmployeeDTO employeeDTO) {
        Assert.notNull(employeeDTO, "Employee cannot be null");

        try {
            // Prepare the employee
            Employee employee = new Employee();
            employee.setBirthDate(employeeDTO.getBirthDate());
            employee.setFirstName(employeeDTO.getFirstName());
            employee.setLastName(employeeDTO.getLastName());
            employee.setGender(employeeDTO.getGender());
            employee.setHireDate(employeeDTO.getHireDate());
            employee.setActiveStatus(null != employeeDTO.getActiveStatus() ? employeeDTO.getActiveStatus() : true);
            employee.setCreatedBy((Long)employeeDTO.getCreatedBy());
            employee.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            employee.setUpdatedBy((Long)employeeDTO.getUpdatedBy());
            employee.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
            
            // Save the employee to get the ID
            Employee savedEmployee = employeeRepository.save(employee);
            
            // Prepare & save the departments
            employeeDTO.getDepartments().forEach(department -> {
                // Get the department entity
                Department deptEntity = departmentService.getDepartmentById(department.getDepartmentId());

                // Create the department employee
                DepartmentEmployee departmentEmployee = new DepartmentEmployee(savedEmployee, deptEntity);
                
                // Set the from and to dates
                departmentEmployee.setFromDate(department.getFromDate());
                departmentEmployee.setToDate(department.getToDate());

                // Save the department employee
                departmentEmployeeService.saveDepartmentEmployee(departmentEmployee);
            });
            
            // Prepare & save the salaries
            employeeDTO.getSalaries().forEach(salary -> {
                // Create the salary employee
                SalaryEmployee salaryEmployee = new SalaryEmployee(savedEmployee, salary.getFromDate());
                
                // Set the amount and to date
                salaryEmployee.setAmount((Long)salary.getAmount());
                salaryEmployee.setToDate(salary.getToDate());

                // Save the salary employee
                salaryEmployeeService.saveSalaryEmployee(salaryEmployee);
            });
            
            // Prepare & save the titles
            employeeDTO.getTitles().forEach(title -> {
                // Create the title employee
                TitleEmployee titleEmployee = new TitleEmployee(savedEmployee, title.getTitle(), title.getFromDate());
                
                // Set the to date
                titleEmployee.setToDate(title.getToDate());

                // Save the title employee
                titleEmployeeService.saveTitleEmployee(titleEmployee);
            });
        } catch (Exception e) {
            throw new RuntimeException("Error saving employee: " + e.getMessage());
        }
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        try {
            // Get all employees
            List<Employee> employees = employeeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

            // Check if the list is empty
            if (employees.isEmpty()) {
                return null;
            }

            // Return the list of employees
            return employees.stream().map(EmployeeDTO::new).toList();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all employees: " + e.getMessage());
        }
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Assert.notNull(id, "Employee id cannot be null");

        try {
            // Get the employee by id
            Employee employee = employeeRepository.findById(id)
                .orElse(null);

            // Check if the employee exists
            if (employee == null) {
                return null;
            }

            // Return the employee
            return new EmployeeDTO(employee);
        } catch (Exception e) {
            throw new RuntimeException("Error getting employee by id: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Assert.notNull(employeeDTO, "Employee cannot be null");
        Assert.notNull(id, "Employee id cannot be null");

        try {
            // Get the existing employee
            Employee existingEmployee = employeeRepository.findById(id)
                .orElse(null);
            
            // Check if the employee exists
            if (existingEmployee == null) {
                return null;
            } else {
                // Update the employee
                existingEmployee.setBirthDate(employeeDTO.getBirthDate());
                existingEmployee.setFirstName(employeeDTO.getFirstName());
                existingEmployee.setLastName(employeeDTO.getLastName());
                existingEmployee.setGender(employeeDTO.getGender());
                existingEmployee.setHireDate(employeeDTO.getHireDate());
                existingEmployee.setActiveStatus(null != employeeDTO.getActiveStatus() ? employeeDTO.getActiveStatus() : existingEmployee.getActiveStatus());
                existingEmployee.setUpdatedBy((Long)employeeDTO.getUpdatedBy());
                existingEmployee.setUpdatedDate(new Timestamp(System.currentTimeMillis()));

                // Remove all childs associated with the employee
                existingEmployee.getDepartments().clear();
                existingEmployee.getSalaries().clear();
                existingEmployee.getTitles().clear();

                // Prepare the departments
                employeeDTO.getDepartments().forEach(department -> {
                    // Get the department entity
                    Department deptEntity = departmentService.getDepartmentById(department.getDepartmentId());

                    // Create the department employee
                    DepartmentEmployee departmentEmployee = new DepartmentEmployee(existingEmployee, deptEntity);
                    
                    // Set the from and to dates
                    departmentEmployee.setFromDate(department.getFromDate());
                    departmentEmployee.setToDate(department.getToDate());

                    // Add the department employee to the list
                    existingEmployee.getDepartments().add(departmentEmployee);
                });

                // Prepare the salaries
                employeeDTO.getSalaries().forEach(salary -> {
                    // Create the salary employee
                    SalaryEmployee salaryEmployee = new SalaryEmployee(existingEmployee, salary.getFromDate());

                    // Set the amount and to date
                    salaryEmployee.setAmount((Long)salary.getAmount());
                    salaryEmployee.setToDate(salary.getToDate());

                    // Add the salary employee to the list
                    existingEmployee.getSalaries().add(salaryEmployee);
                });

                // Prepare the titles
                employeeDTO.getTitles().forEach(title -> {
                    // Create the title employee
                    TitleEmployee titleEmployee = new TitleEmployee(existingEmployee, title.getTitle(), title.getFromDate());

                    // Set the to date
                    titleEmployee.setToDate(title.getToDate());

                    // Add the title employee to the list
                    existingEmployee.getTitles().add(titleEmployee);
                });

                // Save the employee
                return new EmployeeDTO(employeeRepository.save(existingEmployee));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating employee: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public Boolean deleteEmployee(Long id) {
        Assert.notNull(id, "Employee id cannot be null");

        try {
            // Get the employee by id
            Employee employee = employeeRepository.findById(id)
                .orElse(null);

            // Check if the employee exists
            if (employee == null) {
                return false;
            }

            // Delete the employee
            employeeRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error deleting employee: " + e.getMessage());
        }
    }
}
