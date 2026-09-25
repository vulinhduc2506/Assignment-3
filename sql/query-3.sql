--dọn dữ liệu cũ và reset bộ đếm ID
TRUNCATE TABLE ticket_comments, ticket_status_history, tickets, employees RESTART IDENTITY CASCADE;

ALTER TABLE employees ADD COLUMN password_hash VARCHAR(255) NOT NULL;

-- Bonus khi phần bắt buộc đã hoàn thành
ALTER TABLE employees ADD COLUMN role VARCHAR(20) NOT NULL DEFAULT 'USER'
    CHECK (role IN ('USER', 'ADMIN'));
        
INSERT INTO employees (username, full_name, email, active, password_hash, role) VALUES
('employee01', 'Nguyễn Văn A', 'a@test.com', true, '$2a$10$FkByh6rdSCXSRLNb2LmvxuZ215.pZfGRXlVxiqTgMJrt9XowJjNmm', 'USER'),
('employee_inactive', 'Trần Văn B', 'b@test.com', false, '$2a$10$FkByh6rdSCXSRLNb2LmvxuZ215.pZfGRXlVxiqTgMJrt9XowJjNmm', 'USER'),
('admin01', 'Lê Văn C', 'c@test.com', true, '$2a$10$FkByh6rdSCXSRLNb2LmvxuZ215.pZfGRXlVxiqTgMJrt9XowJjNmm', 'ADMIN');
    
INSERT INTO tickets (id, ticket_code, title, description, priority, status, reporter_id, assignee_id, version, created_at, updated_at) VALUES
(1, 'TCK-001', 'Vé chưa có người nhận', 'Dùng để test Case 8', 'HIGH', 'OPEN', 1, NULL, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'TCK-002', 'Vé đang xử lý', 'Dùng để test Case 12 (Đổi người)', 'MEDIUM', 'IN_PROGRESS', 1, 1, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'TCK-003', 'Vé đã đóng', 'Dùng để test Case 11', 'LOW', 'CLOSED', 1, 1, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);