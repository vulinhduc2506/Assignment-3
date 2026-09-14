package com.example.ticket_management.dto.response;

import com.example.ticket_management.enums.TicketStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class HistoryResponse {
    private Long id;
    private TicketStatus fromStatus;
    private TicketStatus toStatus;
    private String changedByName;
    private String note;
    private LocalDateTime changedAt;
}
