CREATE TABLE employees (
    -- TODO [MENTOR REVIEW]: Java dùng Long nhưng SERIAL/FK INT là kiểu 32-bit. Hãy thống nhất kiểu ID
    -- giữa PostgreSQL và Entity (ví dụ BIGSERIAL/BIGINT nếu giữ Long).
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tickets (
    id SERIAL PRIMARY KEY,
    ticket_code VARCHAR(20) NOT NULL UNIQUE,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    priority VARCHAR(20) NOT NULL CHECK (priority IN ('LOW', 'MEDIUM', 'HIGH', 'URGENT')),
    status VARCHAR(50) NOT NULL CHECK (status IN ('OPEN', 'IN_PROGRESS', 'RESOLVED', 'CLOSED')),
    reporter_id INT NOT NULL REFERENCES employees(id),
    assignee_id INT REFERENCES employees(id),
    version INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    resolved_at TIMESTAMP
);

CREATE TABLE ticket_comments (
    id SERIAL PRIMARY KEY,
    ticket_id INT NOT NULL REFERENCES tickets(id),
    author_id INT NOT NULL REFERENCES employees(id),
    content TEXT NOT NULL CHECK (TRIM(content) <> ''),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE ticket_status_history (
    id SERIAL PRIMARY KEY,
    ticket_id INT NOT NULL REFERENCES tickets(id),
    -- TODO [MENTOR REVIEW]: SQL cho phép NULL để lưu trạng thái khởi tạo, nhưng Entity đặt nullable=false.
    -- Chọn một contract thống nhất và giải thích trạng thái lịch sử đầu tiên được biểu diễn thế nào.
    from_status VARCHAR(20),
    to_status VARCHAR(20) NOT NULL,
    changed_by INT NOT NULL REFERENCES employees(id),
    note VARCHAR(255),
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- TODO [MENTOR REVIEW]: Bổ sung index có căn cứ cho các khóa ngoại và điều kiện tìm kiếm thường dùng;
-- không tạo index theo cảm tính, cần nêu query nào được hưởng lợi và chi phí khi INSERT/UPDATE.
