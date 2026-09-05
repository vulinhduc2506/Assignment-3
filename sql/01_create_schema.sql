CREATE TABLE employees (
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
    from_status VARCHAR(20),
    to_status VARCHAR(20) NOT NULL,
    changed_by INT NOT NULL REFERENCES employees(id),
    note VARCHAR(255),
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);