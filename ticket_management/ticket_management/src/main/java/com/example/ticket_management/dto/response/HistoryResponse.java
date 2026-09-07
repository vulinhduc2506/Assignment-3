package com.example.ticket_management.dto.response;

import com.example.ticket_management.enums.TicketStatus;

import java.time.LocalDateTime;

public class HistoryResponse {
    private Long id;
    private TicketStatus fromStatus;
    private TicketStatus toStatus;
    private String changedByName;
    private String note;
    private LocalDateTime changedAt;

    private HistoryResponse(Builder builder) {
        this.id = builder.id;
        this.fromStatus = builder.fromStatus;
        this.toStatus = builder.toStatus;
        this.changedByName = builder.changedByName;
        this.note = builder.note;
        this.changedAt = builder.changedAt;
    }

    public Long getId() { return id; }
    public TicketStatus getFromStatus() { return fromStatus; }
    public TicketStatus getToStatus() { return toStatus; }
    public String getChangedByName() { return changedByName; }
    public String getNote() { return note; }
    public LocalDateTime getChangedAt() { return changedAt; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private TicketStatus fromStatus;
        private TicketStatus toStatus;
        private String changedByName;
        private String note;
        private LocalDateTime changedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder fromStatus(TicketStatus fromStatus) { this.fromStatus = fromStatus; return this; }
        public Builder toStatus(TicketStatus toStatus) { this.toStatus = toStatus; return this; }
        public Builder changedByName(String changedByName) { this.changedByName = changedByName; return this; }
        public Builder note(String note) { this.note = note; return this; }
        public Builder changedAt(LocalDateTime changedAt) { this.changedAt = changedAt; return this; }

        public HistoryResponse build() { return new HistoryResponse(this); }
    }
}
