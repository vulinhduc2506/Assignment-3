package com.example.ticket_management.repository;

import com.example.ticket_management.entity.Employee;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    Optional<Employee> findByUsername(@NotBlank(message = "Username không được để trống") String username);
}
