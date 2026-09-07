package com.example.ticket_management.dto.response;

import com.example.ticket_management.enums.Priority;
import com.example.ticket_management.enums.TicketStatus;

import java.time.LocalDateTime;
import java.util.List;

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

    private TicketResponse(Builder builder) {
        this.id = builder.id;
        this.ticketCode = builder.ticketCode;
        this.title = builder.title;
        this.description = builder.description;
        this.priority = builder.priority;
        this.status = builder.status;
        this.reporterName = builder.reporterName;
        this.assigneeName = builder.assigneeName;
        this.createdAt = builder.createdAt;
        this.resolvedAt = builder.resolvedAt;
        this.comments = builder.comments;
        this.statusHistory = builder.statusHistory;
    }

    public Long getId() { return id; }
    public String getTicketCode() { return ticketCode; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Priority getPriority() { return priority; }
    public TicketStatus getStatus() { return status; }
    public String getReporterName() { return reporterName; }
    public String getAssigneeName() { return assigneeName; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getResolvedAt() { return resolvedAt; }
    public List<CommentResponse> getComments() { return comments; }
    public List<HistoryResponse> getStatusHistory() { return statusHistory; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
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

        public Builder id(Long id) { this.id = id; return this; }
        public Builder ticketCode(String ticketCode) { this.ticketCode = ticketCode; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder priority(Priority priority) { this.priority = priority; return this; }
        public Builder status(TicketStatus status) { this.status = status; return this; }
        public Builder reporterName(String reporterName) { this.reporterName = reporterName; return this; }
        public Builder assigneeName(String assigneeName) { this.assigneeName = assigneeName; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder resolvedAt(LocalDateTime resolvedAt) { this.resolvedAt = resolvedAt; return this; }
        public Builder comments(List<CommentResponse> comments) { this.comments = comments; return this; }
        public Builder statusHistory(List<HistoryResponse> statusHistory) { this.statusHistory = statusHistory; return this; }

        public TicketResponse build() { return new TicketResponse(this); }
    }
}
