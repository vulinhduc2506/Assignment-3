package com.example.ticket_management.repository;

import com.example.ticket_management.entity.Ticket;
import com.example.ticket_management.enums.Priority;
import com.example.ticket_management.enums.TicketStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @EntityGraph(attributePaths = {"reporter", "assignee"})
    Optional<Ticket> findWithUsersById(Long id);

    @Query("SELECT t FROM Ticket t WHERE (:title IS NULL OR LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND " +
            "(:status IS NULL OR t.status = :status) " +
            "AND " +
            "(:priority IS NULL OR t.priority = :priority) " +
            "AND " +
            "(:assigneeId IS NULL OR t.assignee.id = :assigneeId)")
    Page<Ticket> searchTickets(
            @Param("title") String title,
            @Param("status") TicketStatus status,
            @Param("priority") Priority priority,
            @Param("assigneeId") Long assigneeId,
            Pageable pageable);
}
