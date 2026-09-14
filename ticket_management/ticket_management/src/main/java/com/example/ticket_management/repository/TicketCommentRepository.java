package com.example.ticket_management.repository;

import com.example.ticket_management.entity.TicketComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketCommentRepository extends JpaRepository<TicketComment, Long> {
    // TODO [MENTOR REVIEW]: getTicketDetails() đọc author.fullName của từng comment trong vòng lặp.
    // Hãy thiết kế fetch plan phù hợp và thêm ORDER BY createdAt để thứ tự response ổn định.
    List<TicketComment> findByTicketId(Long ticketId);
}
