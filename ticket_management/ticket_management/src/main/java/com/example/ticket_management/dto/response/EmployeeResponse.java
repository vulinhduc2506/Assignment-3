package com.example.ticket_management.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmployeeResponse {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private Boolean active;
}
