package com.example.ticket_management.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CommentResponse {
    private Long id;
    private String authorName;
    private String content;
    private LocalDateTime createdAt;

}
