package com.example.ticket_management.repository;

import com.example.ticket_management.entity.Ticket;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @EntityGraph(attributePaths = {"reporter", "assignee"})
    Optional<Ticket> findWithUsersById(Long id);
}
