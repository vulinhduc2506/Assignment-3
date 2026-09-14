package com.example.ticket_management.service;

import com.example.ticket_management.dto.request.CommentCreateRequest;
import com.example.ticket_management.dto.request.TicketAssignRequest;
import com.example.ticket_management.dto.request.TicketCreateRequest;
import com.example.ticket_management.dto.request.TicketTransitionRequest;
import com.example.ticket_management.dto.response.CommentResponse;
import com.example.ticket_management.dto.response.HistoryResponse;
import com.example.ticket_management.dto.response.TicketResponse;
import com.example.ticket_management.entity.Employee;
import com.example.ticket_management.entity.Ticket;
import com.example.ticket_management.entity.TicketComment;
import com.example.ticket_management.entity.TicketStatusHistory;
import com.example.ticket_management.enums.Priority;
import com.example.ticket_management.enums.TicketAction;
import com.example.ticket_management.enums.TicketStatus;
import com.example.ticket_management.exception.AppException;
import com.example.ticket_management.exception.ErrorCode;
import com.example.ticket_management.repository.EmployeeRepository;
import com.example.ticket_management.repository.TicketCommentRepository;
import com.example.ticket_management.repository.TicketRepository;
import com.example.ticket_management.repository.TicketStatusHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final EmployeeRepository employeeRepository;
    private final TicketCommentRepository ticketCommentRepository;
    private final TicketStatusHistoryRepository ticketStatusHistoryRepository;

    @Transactional
    public TicketResponse createTicket(TicketCreateRequest request) {
        Employee reporter = employeeRepository.findById(request.getReporterId())
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));

        if (!reporter.isActive()) {
            throw new AppException(ErrorCode.EMPLOYEE_INACTIVE);
        }

        Ticket ticket = new Ticket();

        ticket.setTicketCode("TCK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setPriority(request.getPriority());
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setReporter(reporter);

        Ticket savedTicket = ticketRepository.save(ticket);

        return TicketResponse.builder()
                .id(savedTicket.getId())
                .ticketCode(savedTicket.getTicketCode())
                .title(savedTicket.getTitle())
                .description(savedTicket.getDescription())
                .priority(savedTicket.getPriority())
                .status(savedTicket.getStatus())
                .reporterName(savedTicket.getReporter().getFullName())
                .createdAt(savedTicket.getCreatedAt())
                .build();
    }

    @Transactional
    public TicketResponse getTicketDetails(Long id) {
        Ticket ticket = ticketRepository.findWithUsersById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TICKET_NOT_FOUND));

        List<TicketComment> comments = ticketCommentRepository.findByTicketIdOrderByCreatedAtDesc(id);
        List<TicketStatusHistory> histories = ticketStatusHistoryRepository.findByTicket_IdOrderByChangedAtDesc(id);

        List<CommentResponse> commentResponses = comments.stream()
                .map(c -> CommentResponse.builder()
                        .id(c.getId())
                        .authorName(c.getAuthor().getFullName())
                        .content(c.getContent())
                        .createdAt(c.getCreatedAt())
                        .build()).toList();

        List<HistoryResponse> historyResponses = histories.stream()
                .map(h -> HistoryResponse.builder()
                        .id(h.getId())
                        .fromStatus(h.getFromStatus())
                        .toStatus(h.getToStatus())
                        .changedByName(h.getChangedBy().getFullName())
                        .note(h.getNote())
                        .changedAt(h.getChangedAt())
                        .build()).toList();

        return TicketResponse.builder()
                .id(ticket.getId())
                .ticketCode(ticket.getTicketCode())
                .title(ticket.getTitle())
                .description(ticket.getDescription())
                .priority(ticket.getPriority())
                .status(ticket.getStatus())
                .reporterName(ticket.getReporter().getFullName())
                .assigneeName(ticket.getAssignee() != null ? ticket.getAssignee().getFullName() : null)
                .createdAt(ticket.getCreatedAt())
                .resolvedAt(ticket.getResolvedAt())
                .comments(commentResponses)
                .statusHistory(historyResponses)
                .build();
    }

    @Transactional
    public TicketResponse assignTicket(Long ticketId, TicketAssignRequest request) {
        Ticket ticket = ticketRepository.findWithUsersById(ticketId)
                .orElseThrow(() -> new AppException(ErrorCode.TICKET_NOT_FOUND));

        if (ticket.getStatus() == TicketStatus.CLOSED) {
            throw new AppException(ErrorCode.INVALID_STATUS_TRANSITION);
        }

        Employee assignee = employeeRepository.findById(request.getAssigneeId())
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));

        if (!assignee.isActive()) {
            throw new AppException(ErrorCode.EMPLOYEE_INACTIVE);
        }

        ticket.setAssignee(assignee);

        return TicketResponse.builder()
                .id(ticket.getId())
                .ticketCode(ticket.getTicketCode())
                .title(ticket.getTitle())
                .description(ticket.getDescription())
                .priority(ticket.getPriority())
                .status(ticket.getStatus())
                .reporterName(ticket.getReporter().getFullName())
                .assigneeName(ticket.getAssignee().getFullName())
                .createdAt(ticket.getCreatedAt())
                .resolvedAt(ticket.getResolvedAt())
                .build();

    }

    @Transactional
    public CommentResponse addComment(Long ticketId, CommentCreateRequest request) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new AppException(ErrorCode.TICKET_NOT_FOUND));

        if (ticket.getStatus() == TicketStatus.CLOSED) {
            throw new AppException(ErrorCode.COMMENT_NOT_ALLOWED);
        }

        Employee author = employeeRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));

        if (!author.isActive()) {
            throw new AppException(ErrorCode.EMPLOYEE_INACTIVE);
        }

        TicketComment comment = new TicketComment();
        comment.setTicket(ticket);
        comment.setAuthor(author);
        comment.setContent(request.getContent());

        TicketComment savedComment = ticketCommentRepository.save(comment);

        return CommentResponse.builder()
                .id(savedComment.getId())
                .authorName(savedComment.getAuthor().getFullName())
                .content(savedComment.getContent())
                .createdAt(savedComment.getCreatedAt())
                .build();
    }

    @Transactional
    public TicketResponse changeStatus(Long ticketId, TicketTransitionRequest request) {
        Ticket ticket = ticketRepository.findWithUsersById(ticketId)
                .orElseThrow(() -> new AppException(ErrorCode.TICKET_NOT_FOUND));

        Employee actor = employeeRepository.findById(request.getActorId())
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));

        if (!actor.isActive()) {
            throw new AppException(ErrorCode.EMPLOYEE_INACTIVE);
        }

        boolean hasAssignee = ticket.getAssignee() != null;
        boolean actorIsAssignee = hasAssignee && ticket.getAssignee().getId().equals(actor.getId());
        boolean actorIsReporter = ticket.getReporter().getId().equals(actor.getId());

        // 2. Gọi hàm State Machine thuần Java (Ép Client dùng Action, không dùng Status)
        TicketStatus nextStatus = determineNextStatus(
                ticket.getStatus(),
                request.getAction(), // DTO đã được sửa thành Action
                hasAssignee,
                actorIsAssignee,
                actorIsReporter,
                request.getNote()
        );

        // 3. Khởi tạo lịch sử
        TicketStatusHistory history = new TicketStatusHistory();
        history.setTicket(ticket);
        history.setFromStatus(ticket.getStatus());
        history.setToStatus(nextStatus);
        history.setChangedBy(actor);
        history.setNote(request.getNote());

        // 4. Cập nhật Ticket
        ticket.setStatus(nextStatus);
        if (nextStatus == TicketStatus.RESOLVED) {
            ticket.setResolvedAt(LocalDateTime.now());
        } else if (nextStatus == TicketStatus.IN_PROGRESS && ticket.getResolvedAt() != null) {
            ticket.setResolvedAt(null);
        }

        // 5. Lưu lịch sử (Ticket tự động lưu nhờ Dirty Checking)
        ticketStatusHistoryRepository.save(history);

        return TicketResponse.builder()
                .id(ticket.getId())
                .ticketCode(ticket.getTicketCode())
                .title(ticket.getTitle())
                .status(ticket.getStatus())
                .reporterName(ticket.getReporter().getFullName())
                .assigneeName(hasAssignee ? ticket.getAssignee().getFullName() : null)
                .createdAt(ticket.getCreatedAt())
                .resolvedAt(ticket.getResolvedAt())
                .build();
    }

    // TODO [MENTOR REVIEW]: Method không có transaction đọc và query không fetch reporter/assignee.
    // Việc map hai quan hệ LAZY có thể gây N+1 hoặc LazyInitializationException tùy cấu hình OSIV.
    @Transactional
    public Page<TicketResponse> searchTickets(String keyword, TicketStatus status, Priority priority, Long assigneeId, Pageable pageable) {
        Page<Ticket> ticketPage = ticketRepository.searchTickets(keyword, status, priority, assigneeId, pageable);

        return ticketPage.map(ticket -> TicketResponse.builder()
                .id(ticket.getId())
                .ticketCode(ticket.getTicketCode())
                .title(ticket.getTitle())
                .description(ticket.getDescription())
                .priority(ticket.getPriority())
                .status(ticket.getStatus())
                .reporterName(ticket.getReporter().getFullName())
                .assigneeName(ticket.getAssignee() != null ? ticket.getAssignee().getFullName() : null)
                .createdAt(ticket.getCreatedAt())
                .resolvedAt(ticket.getResolvedAt())
                .build());
    }

    public TicketStatus determineNextStatus(
            TicketStatus currentStatus,
            TicketAction action,
            boolean hasAssignee,
            boolean actorIsAssignee,
            boolean actorIsReporter,
            String note) {
        switch (currentStatus) {
            case OPEN:
                if (action == TicketAction.START && actorIsAssignee && hasAssignee) {
                    return TicketStatus.IN_PROGRESS;
                }
                break;

            case IN_PROGRESS:
                if (action == TicketAction.RESOLVE && isValidNote(note)) {
                    return TicketStatus.RESOLVED;
                }
                break;

            case RESOLVED:
                if (action == TicketAction.CLOSE && actorIsReporter) {
                    return TicketStatus.CLOSED;
                }
                if (action == TicketAction.REOPEN && isValidNote(note)) {
                    return TicketStatus.IN_PROGRESS;
                }
                break;
            case CLOSED:
                break;
        }
        throw new IllegalArgumentException("Thao tác chuyển trạng thái không hợp lệ");
    }

    private boolean isValidNote(String note) {
        return note != null && !note.trim().isBlank();
    }

}
