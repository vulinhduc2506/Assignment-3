package com.example.ticket_management.repository;

import com.example.ticket_management.entity.TicketComment;
import com.example.ticket_management.entity.TicketStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketStatusHistoryRepository extends JpaRepository<TicketStatusHistory, Long> {
    List<TicketStatusHistory> findByTicketId(Long ticketId);
}
