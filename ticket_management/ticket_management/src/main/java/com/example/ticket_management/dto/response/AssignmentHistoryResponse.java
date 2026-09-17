package com.example.ticket_management.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
public class AssignmentHistoryResponse {
    private Long id;
    private Long oldAssigneeId;
    private String oldAssigneeName;
    private Long newAssigneeId;
    private String newAssigneeName;
    private Long changedById;
    private String changedByName;
    private String reason;
    private LocalDateTime changedAt;

}
