package com.example.ticket_management.repository;

import com.example.ticket_management.entity.TicketStatusHistory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketStatusHistoryRepository extends JpaRepository<TicketStatusHistory, Long> {
    @EntityGraph(attributePaths = {"changedBy"})
    List<TicketStatusHistory> findByTicket_IdOrderByChangedAtDesc(Long ticketId);
}
