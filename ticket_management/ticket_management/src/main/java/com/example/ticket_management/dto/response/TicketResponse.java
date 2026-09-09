package com.example.ticket_management.dto.response;

import com.example.ticket_management.enums.Priority;
import com.example.ticket_management.enums.TicketStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class TicketResponse {
    private Long id;
    private String ticketCode;
    private String title;
    private String description;
    private Priority priority;
    private TicketStatus status;
    private String reporterName;
    private String assigneeName;
    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;

    private List<CommentResponse> comments;
    private List<HistoryResponse> statusHistory;

}
