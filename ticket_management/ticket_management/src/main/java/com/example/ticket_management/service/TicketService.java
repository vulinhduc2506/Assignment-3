package com.example.ticket_management.service;

import com.example.ticket_management.dto.request.TicketCreateRequest;
import com.example.ticket_management.dto.response.TicketResponse;
import com.example.ticket_management.entity.Employee;
import com.example.ticket_management.entity.Ticket;
import com.example.ticket_management.enums.TicketStatus;
import com.example.ticket_management.exception.AppException;
import com.example.ticket_management.exception.ErrorCode;
import com.example.ticket_management.repository.EmployeeRepository;
import com.example.ticket_management.repository.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final EmployeeRepository employeeRepository;

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
}
