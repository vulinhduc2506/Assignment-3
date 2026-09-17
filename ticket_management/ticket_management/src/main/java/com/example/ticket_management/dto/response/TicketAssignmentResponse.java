package com.example.ticket_management.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class TicketAssignmentResponse {
    private Long ticketId;
    private Long oldAssigneeId;
    private Long newAssigneeId;
    private Long changedBy;
    private String reason;
    private LocalDateTime changedAt;

}
