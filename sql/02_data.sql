-- 1. THÊM 5 NHÂN VIÊN
INSERT INTO employees (username, full_name, email, active) VALUES
('reporter1', 'Nguyễn Báo Cáo', 'reporter1@test.com', true),
('assignee_a', 'Trần Chuyên Viên A', 'a@test.com', true),
('assignee_b', 'Lê Chuyên Viên B', 'b@test.com', true),
('assignee_c', 'Phạm Chuyên Viên C', 'c@test.com', true),
('ngoi_choi', 'Hoàng Ngồi Chơi', 'idle@test.com', true);


-- 2. THÊM 10 TICKET VỚI
INSERT INTO tickets (ticket_code, title, description, priority, status, reporter_id, assignee_id, version, created_at, resolved_at) VALUES
-- TCK-001 (ID=1): Ticket chưa phân công (Dành cho Q-01) -> assignee_id = NULL
('TCK-001', 'Lỗi màn hình', 'Màn hình xanh', 'HIGH', 'OPEN', 1, NULL, 0, CURRENT_TIMESTAMP - INTERVAL '10 days', NULL),

-- TCK-002, 003 (ID=2, 3): Bình thường
('TCK-002', 'Lỗi chuột', 'Chuột hỏng', 'URGENT', 'IN_PROGRESS', 1, 2, 0, CURRENT_TIMESTAMP - INTERVAL '9 days', NULL),
('TCK-003', 'Cài win', 'Cài lại win 11', 'HIGH', 'RESOLVED', 1, 2, 0, CURRENT_TIMESTAMP - INTERVAL '15 days', CURRENT_TIMESTAMP - INTERVAL '10 days'),

-- TCK-004 (ID=4): Đã RESOLVED quá 30 ngày (Dành cho Q-03: Tính KPI 30 ngày gần nhất)
('TCK-004', 'Lỗi phím', 'Phím kẹt', 'MEDIUM', 'RESOLVED', 1, 2, 0, CURRENT_TIMESTAMP - INTERVAL '45 days', CURRENT_TIMESTAMP - INTERVAL '40 days'),

-- TCK-005 -> 008 (ID=5 -> 8): Đa dạng để test Gom nhóm (Dành cho Q-02)
('TCK-005', 'Hỏng mạng', 'Mất mạng', 'LOW', 'RESOLVED', 1, 3, 0, CURRENT_TIMESTAMP - INTERVAL '10 days', CURRENT_TIMESTAMP - INTERVAL '5 days'),
('TCK-006', 'Thay mực in', 'Hết mực', 'URGENT', 'CLOSED', 1, 4, 0, CURRENT_TIMESTAMP - INTERVAL '20 days', CURRENT_TIMESTAMP - INTERVAL '18 days'),
('TCK-007', 'Lỗi mail', 'Không gửi mail', 'MEDIUM', 'IN_PROGRESS', 1, 3, 0, CURRENT_TIMESTAMP - INTERVAL '2 days', NULL),
('TCK-008', 'Cấp tài khoản', 'Cấp acc mới', 'HIGH', 'RESOLVED', 1, 4, 0, CURRENT_TIMESTAMP - INTERVAL '20 days', CURRENT_TIMESTAMP - INTERVAL '15 days'),

-- TCK-009 (ID=9): Boss Q-06 -> Bảng này báo RESOLVED, nhưng bảng Lịch sử lát nữa sẽ báo CLOSED
('TCK-009', 'Lỗi Data', 'Kiểm tra độ lệch', 'HIGH', 'RESOLVED', 1, 2, 0, CURRENT_TIMESTAMP - INTERVAL '5 days', CURRENT_TIMESTAMP - INTERVAL '2 days'),

-- TCK-010 (ID=10): Ticket lãng quên (Dành cho Q-07: > 5 comment, comment cuối cách đây 4 ngày)
('TCK-010', 'Nhiều comment', 'Quá hạn xử lý', 'LOW', 'OPEN', 1, 2, 0, CURRENT_TIMESTAMP - INTERVAL '10 days', NULL);

-- 3. THÊM LỊCH SỬ CHUYỂN TRẠNG THÁI (Lấy 3 Ticket tiêu biểu)
INSERT INTO ticket_status_history (ticket_id, from_status, to_status, changed_by, changed_at) VALUES
-- Lịch sử chuẩn của Ticket 3
(3, NULL, 'OPEN', 1, CURRENT_TIMESTAMP - INTERVAL '15 days'),
(3, 'OPEN', 'IN_PROGRESS', 2, CURRENT_TIMESTAMP - INTERVAL '13 days'),
(3, 'IN_PROGRESS', 'RESOLVED', 2, CURRENT_TIMESTAMP - INTERVAL '10 days'),

-- Lịch sử quá 30 ngày của Ticket 4
(4, NULL, 'OPEN', 1, CURRENT_TIMESTAMP - INTERVAL '45 days'),
(4, 'OPEN', 'RESOLVED', 2, CURRENT_TIMESTAMP - INTERVAL '40 days'),

-- Lịch sử LỖI của Ticket 9 (Lịch sử chốt là CLOSED, nhưng bảng Tickets ở trên lại đang là RESOLVED)
(9, NULL, 'OPEN', 1, CURRENT_TIMESTAMP - INTERVAL '5 days'),
(9, 'OPEN', 'RESOLVED', 2, CURRENT_TIMESTAMP - INTERVAL '2 days'),
(9, 'RESOLVED', 'CLOSED', 2, CURRENT_TIMESTAMP - INTERVAL '1 days'); 


-- 4. THÊM BÌNH LUẬN (Bơm 6 bình luận cho TCK-010, comment cuối là 4 ngày trước)
INSERT INTO ticket_comments (ticket_id, author_id, content, created_at) VALUES
(10, 1, 'Bình luận 1: Đã tiếp nhận', CURRENT_TIMESTAMP - INTERVAL '8 days'),
(10, 2, 'Bình luận 2: Đang xử lý', CURRENT_TIMESTAMP - INTERVAL '7 days'),
(10, 1, 'Bình luận 3: Xong chưa em?', CURRENT_TIMESTAMP - INTERVAL '6 days'),
(10, 2, 'Bình luận 4: Chờ linh kiện', CURRENT_TIMESTAMP - INTERVAL '5 days'),
(10, 3, 'Bình luận 5: Khách hối quá', CURRENT_TIMESTAMP - INTERVAL '5 days'),
(10, 1, 'Bình luận 6: Lần cuối cập nhật', CURRENT_TIMESTAMP - INTERVAL '4 days'), -- Đã quá 3 ngày

-- Thêm vài comment rác cho ticket khác
(1, 1, 'Cần gấp nhé', CURRENT_TIMESTAMP - INTERVAL '9 days'),
(3, 2, 'Đã hoàn thành xuất sắc', CURRENT_TIMESTAMP - INTERVAL '10 days');