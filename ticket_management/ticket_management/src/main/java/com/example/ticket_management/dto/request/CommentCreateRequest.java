package com.example.ticket_management.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateRequest {
    @NotNull(message = "ID người bình luận không được trống")
    private Long authorId;

    @NotBlank(message = "Nội dung bình luận không được để trống")
    private String content;
}
