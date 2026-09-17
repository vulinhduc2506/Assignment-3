--1
SELECT * FROM ticket_assignment_history 
WHERE ticket_id = 1 
ORDER BY changed_at DESC, id DESC;
--2
SELECT e.id, e.full_name, COUNT(t.id) AS total_tickets
FROM employees e
LEFT JOIN tickets t ON e.id = t.assignee_id
GROUP BY e.id, e.full_name;
--3
SELECT e.id, e.full_name, COUNT(t.id) AS pending_tickets
FROM employees e
JOIN tickets t ON e.id = t.assignee_id
WHERE t.status != 'CLOSED'
GROUP BY e.id, e.full_name
ORDER BY pending_tickets DESC
LIMIT 1;
--4
SELECT ticket_id, COUNT(id) AS reassign_count
FROM ticket_assignment_history
GROUP BY ticket_id
HAVING COUNT(id) > 2;
--5
SELECT t.* 
FROM tickets t
LEFT JOIN ticket_assignment_history h ON t.id = h.ticket_id
WHERE h.id IS NULL;
--6
SELECT DISTINCT ON (ticket_id) *
FROM ticket_assignment_history
ORDER BY ticket_id, changed_at DESC, id DESC;
--7
SELECT t.id, t.title, r.full_name AS reporter, a.full_name AS assignee
FROM tickets t
JOIN employees r ON t.reporter_id = r.id
LEFT JOIN employees a ON t.assignee_id = a.id;
--8
SELECT e.id, e.username, e.full_name
FROM employees e
WHERE NOT EXISTS (
    -- Kiểm tra ở hiện tại (bảng tickets)
    SELECT 1 FROM tickets t WHERE t.assignee_id = e.id
)
AND NOT EXISTS (
    -- Kiểm tra trong quá khứ (bảng lịch sử phân công)
    SELECT 1 FROM ticket_assignment_history h 
    WHERE h.new_assignee_id = e.id OR h.old_assignee_id = e.id
);
--9
INSERT INTO ticket_assignment_history (ticket_id, new_assignee_id, changed_by, changed_at) 
VALUES (99999, 1, 1, NOW());
--ticket_id 99999 không hề tồn tại trong bảng tickets. Khóa ngoại ngăn chặn việc chúng ta trỏ tới một dữ liệu ảo.
--10
INSERT INTO ticket_assignment_history (ticket_id, new_assignee_id, changed_by, reason, changed_at) 
VALUES (1, 1, 1, '     ', NOW());
--ràng buộc `CHECK (TRIM(reason) <> '')`. Đoạn dữ liệu toàn dấu cách kia khi chạy qua hàm TRIM() sẽ biến thành rỗng, vi phạm ngay rule bảo vệ toàn vẹn dữ liệu của Database

