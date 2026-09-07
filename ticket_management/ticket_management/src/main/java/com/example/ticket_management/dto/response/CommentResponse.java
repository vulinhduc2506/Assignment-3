package com.example.ticket_management.dto.response;

import java.time.LocalDateTime;

public class CommentResponse {
    private Long id;
    private String authorName;
    private String content;
    private LocalDateTime createdAt;

    private CommentResponse(Builder builder) {
        this.id = builder.id;
        this.authorName = builder.authorName;
        this.content = builder.content;
        this.createdAt = builder.createdAt;
    }

    public Long getId() { return id; }
    public String getAuthorName() { return authorName; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String authorName;
        private String content;
        private LocalDateTime createdAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder authorName(String authorName) {
            this.authorName = authorName;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public CommentResponse build() {
            return new CommentResponse(this);
        }
    }
}
