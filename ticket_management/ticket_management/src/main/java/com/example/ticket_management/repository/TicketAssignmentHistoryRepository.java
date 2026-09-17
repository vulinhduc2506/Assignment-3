package com.example.ticket_management.repository;
import com.example.ticket_management.entity.TicketAssignmentHistory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketAssignmentHistoryRepository extends JpaRepository<TicketAssignmentHistory, Long> {
    @EntityGraph(attributePaths = {"oldAssignee", "newAssignee","changedBy"})
    List<TicketAssignmentHistory> findByOrderByChangedAtDescIdDesc(Long ticketId);
}
