package com.example.ticket_management.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Setter
@Getter
public class TicketAssignRequest {
    @NotNull(message = "Bắt buộc truyền ID của assignee")
    private Long assigneeId;
}
