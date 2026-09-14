package com.example.ticket_management.repository;

import com.example.ticket_management.entity.TicketStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketStatusHistoryRepository extends JpaRepository<TicketStatusHistory, Long> {
    // TODO [MENTOR REVIEW]: Field hiện tên ticketId nhưng kiểu lại là Ticket, làm derived query khó hiểu.
    // Hãy đặt tên quan hệ là ticket và dùng property path rõ nghĩa; đồng thời fetch changedBy và sắp xếp changedAt.
    List<TicketStatusHistory> findByTicketId(Long ticketId);
}
