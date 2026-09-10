package com.example.ticket_management.controller;

import com.example.ticket_management.dto.request.CommentCreateRequest;
import com.example.ticket_management.dto.request.TicketAssignRequest;
import com.example.ticket_management.dto.request.TicketCreateRequest;
import com.example.ticket_management.dto.request.TicketTransitionRequest;
import com.example.ticket_management.dto.response.CommentResponse;
import com.example.ticket_management.dto.response.TicketResponse;
import com.example.ticket_management.enums.Priority;
import com.example.ticket_management.enums.TicketStatus;
import com.example.ticket_management.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(@Valid @RequestBody TicketCreateRequest request) {
        TicketResponse response = ticketService.createTicket(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<TicketResponse> getTicketDetails(@PathVariable Long id) {
        TicketResponse response = ticketService.getTicketDetails(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/assignee")
    public ResponseEntity<TicketResponse> assignTicket(
            @PathVariable Long id,
            @Valid @RequestBody TicketAssignRequest request) {
        TicketResponse response = ticketService.assignTicket(id, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentResponse> commentTicket(
            @PathVariable Long id,
            @Valid @RequestBody CommentCreateRequest request
            ) {
        CommentResponse response = ticketService.addComment(id, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/transitions")
    public ResponseEntity<TicketResponse> changeStatus(
            @PathVariable Long id,
            @Valid @RequestBody TicketTransitionRequest request) {
        TicketResponse response = ticketService.changeStatus(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<TicketResponse>> searchTickets(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) TicketStatus status,
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) Long assigneeId,
            Pageable pageable) {
        Page<TicketResponse> responsePage = ticketService.searchTickets(keyword, status, priority, assigneeId, pageable);
        return ResponseEntity.ok(responsePage);
    }
}
