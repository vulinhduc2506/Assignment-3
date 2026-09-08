package com.example.ticket_management.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    EMPLOYEE_NOT_FOUND(HttpStatus.NOT_FOUND, "Nhân viên không tồn tại"),
    EMPLOYEE_INACTIVE(HttpStatus.BAD_REQUEST, "Nhân viên đang bị vô hiệu hóa"),
    TICKET_NOT_FOUND(HttpStatus.NOT_FOUND, "Ticket không tồn tại"),
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "Username hoặc email đã tồn tại"),
    INVALID_STATUS_TRANSITION(HttpStatus.CONFLICT, "Không thể chuyển sang trạng thái này"),
    ASSIGNEE_REQUIRED(HttpStatus.BAD_REQUEST, "Bắt buộc phải có người xử lý"),
    COMMENT_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "Không thể comment vào ticket đã đóng"),
    CONCURRENT_UPDATE(HttpStatus.CONFLICT, "Dữ liệu đã bị thay đổi");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }



}
