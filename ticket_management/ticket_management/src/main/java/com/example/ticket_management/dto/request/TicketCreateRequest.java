package com.example.ticket_management.dto.request;

import com.example.ticket_management.enums.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketCreateRequest {
    @NotBlank(message = "Tiêu đề không được để trống")
    private String title;

    @NotBlank(message = "Mô tả không được để trống")
    private String description;

    @NotNull(message = "Độ ưu tiên không được để trống")
    private Priority priority;

    @NotNull(message = "ID người báo cáo không được để trống")
    private Long reporterId;

}