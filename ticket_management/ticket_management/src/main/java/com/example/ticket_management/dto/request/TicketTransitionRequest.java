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

    // TODO [MENTOR REVIEW]: Client không nên tự chọn trạng thái đích vì có thể bỏ qua state machine.
    // Request cần nhận hành động nghiệp vụ START/RESOLVE/CLOSE/REOPEN; service tự xác định trạng thái tiếp theo.
    @NotNull(message = "Trạng thái mới không được để trống")
    private TicketStatus newStatus;

    private String note;
}
