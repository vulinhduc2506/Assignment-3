package com.example.ticket_management.dto.request;

import com.example.ticket_management.enums.TicketStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketTransitionRequest {
    @NotNull(message = "Bắt buộc phải truyền ID người thao tác")
    private Long actorId;

    @NotNull(message = "Trạng thái mới không được để trống")
    private TicketStatus newStatus;

    private String note;
}
