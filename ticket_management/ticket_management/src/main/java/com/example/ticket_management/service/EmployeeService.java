package com.example.ticket_management.service;

import com.example.ticket_management.dto.request.EmployeeCreateRequest;
import com.example.ticket_management.dto.response.EmployeeResponse;
import com.example.ticket_management.entity.Employee;
import com.example.ticket_management.exception.AppException;
import com.example.ticket_management.exception.ErrorCode;
import com.example.ticket_management.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Transactional
    public EmployeeResponse createEmployee(EmployeeCreateRequest request) {
        if (employeeRepository.existsByUsername(request.getUsername()) ||
                employeeRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.DUPLICATE_USERNAME);
        }

        Employee employee = new Employee();
        employee.setUsername(request.getUsername());
        employee.setFullName(request.getFullName());
        employee.setEmail(request.getEmail());
        employee.setActive(true);

        Employee savedEmployee = employeeRepository.save(employee);

        return EmployeeResponse.builder()
                .id(savedEmployee.getId())
                .username(savedEmployee.getUsername())
                .fullName(savedEmployee.getUsername())
                .email(savedEmployee.getEmail())
                .active(savedEmployee.isActive())
                .build();
    }
}
