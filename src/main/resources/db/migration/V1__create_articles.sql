CREATE TABLE article (
    id INTEGER PRIMARY KEY,
    title TEXT NOT NULL,
    content TEXT NOT NULL,
    status TEXT NOT NULL CHECK (
    status IN ('PENDING', 'MODERATING', 'REJECTED', 'PUBLISHED')),
    published_at TIMESTAMPTZ
);