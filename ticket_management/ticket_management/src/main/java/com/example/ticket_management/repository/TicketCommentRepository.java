package com.example.ticket_management.repository;

import com.example.ticket_management.entity.TicketComment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketCommentRepository extends JpaRepository<TicketComment, Long> {
    @EntityGraph(attributePaths = {"author"})
    List<TicketComment> findByTicketIdOrderByCreatedAtDesc(Long ticketId);
}
