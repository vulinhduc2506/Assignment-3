--1
-- Mục đích: truy xuất các ticket chưa xử lí xong, nhằm theo dõi tiến độ
-- Dùng INNER JOIN để lấy reporter_id
-- Dùng LEFT JOIN với asignee_id thì các ticket đang thiếu assignee vẫn sẽ xuất hiện
SELECT t.ticket_code, t.title, r.full_name AS reporter_name, a.full_name AS assignee_name
FROM tickets t
INNER JOIN employees r ON t.reporter_id = r.id
LEFT JOIN employees a ON t.assignee_id = a.id
WHERE t.status != 'CLOSED';

--2
-- Mục đích: truy xuất ticket theo trạng thái và độ ưu tiên để theo dõi tiến độ, và xem ticket nào cần phải xử lí trước
-- dùng GROUP BY để tính số lượng ticket theo status và priority
SELECT status, priority, COUNT(id) AS total_tickets
FROM tickets
GROUP BY status, priority;

--3
-- Mục đích: nhằm tính KPI của các assignee đã giải quyết được bao nhiêu ticket trong 1 tháng
-- cùng GROUP BY để count ticket theo assignee
-- CURRENT_DATE(ngày hôm nay) - INTERVAL '30 days' (một khoảng, cụ thể 30 ngày) 
SELECT assignee_id, COUNT(id) AS resolved_count
FROM tickets
WHERE status = 'RESOLVED' AND resolved_at >= CURRENT_DATE - INTERVAL '30 days'
GROUP BY assignee_id
ORDER BY resolved_count DESC
LIMIT 3;

--4
-- Mục đích: xem employee nào đang rảnh để phân chia công việc tối ưu nguồn lực
-- Dùng LEFT JOIN từ employee sang ticket theo assignee_id sẽ lấy tất cả employee kể cả họ ko có ticket nào,
--sau đó thêm điều kiện ticket.id NULL thì sẽ lọc ra những người ko có việc
SELECT e.id, e.full_name
FROM employees e
LEFT JOIN tickets t ON e.id = t.assignee_id
WHERE t.id IS NULL;

--5
-- Mục đích: xem tốc độ làm việc của các assignee
-- có thể trừ trực tiếp ngày sẽ ra thời gian xử lí 1 ticket sau đó kết hợp hàm AVG sẽ ra thời gian trung bình
SELECT assignee_id, AVG(resolved_at - created_at) AS avg_processing_time
FROM tickets
WHERE resolved_at IS NOT NULL
GROUP BY assignee_id;

--7
-- Mục đích: tìm kiếm các ticket gặp nhiều vấn đề thời gian gần đây để giục xủ lí
-- bắt buộc dùng HAVING sau GROUP BY
-- Các hàm tổng hợp cần đặt sau HAVING
SELECT t.id, t.ticket_code, COUNT(tc.id) AS total_comments
FROM tickets t
JOIN ticket_comments tc ON t.id = tc.ticket_id
GROUP BY t.id, t.ticket_code
HAVING COUNT(tc.id) > 5 
   AND MAX(tc.created_at) < CURRENT_TIMESTAMP - INTERVAL '3 days';
  
-- 6. Mục đích: Truy quét dữ liệu rác (Lệch trạng thái giữa bảng Ticket và History)
WITH RankedHistory AS (
    SELECT ticket_id, to_status,
           ROW_NUMBER() OVER(PARTITION BY ticket_id ORDER BY changed_at DESC, id DESC) as rn
    FROM ticket_status_history
)
SELECT t.ticket_code, 
       t.status AS current_status, 
       rh.to_status AS latest_history_status
FROM tickets t
INNER JOIN RankedHistory rh ON t.id = rh.ticket_id AND rh.rn = 1
WHERE t.status != rh.to_status;
