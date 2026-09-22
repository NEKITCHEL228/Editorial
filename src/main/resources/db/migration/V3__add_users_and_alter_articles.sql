CREATE TABLE roles (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    code VARCHAR(32) NOT NULL UNIQUE
);

INSERT INTO roles (code)
VALUES
    ('ADMIN'),
    ('EDITOR'),
    ('AUTHOR');


CREATE TABLE users (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash TEXT NOT NULL,
    role_id INTEGER NOT NULL,

    CONSTRAINT fk_users_role
        FOREIGN KEY (role_id)
        REFERENCES roles(id)
        ON DELETE RESTRICT
);

CREATE INDEX idx_users_role_id
ON users(role_id);


CREATE TABLE article_statuses (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    code VARCHAR(32) NOT NULL UNIQUE
);

INSERT INTO article_statuses (code)
VALUES
    ('PENDING'),
    ('MODERATING'),
    ('REJECTED'),
    ('PUBLISHED');


ALTER TABLE articles
ADD COLUMN author_id INTEGER NOT NULL;

ALTER TABLE articles
ADD COLUMN status_id INTEGER NOT NULL;


ALTER TABLE articles
ADD CONSTRAINT fk_articles_author
    FOREIGN KEY (author_id)
    REFERENCES users(id)
    ON DELETE RESTRICT;

ALTER TABLE articles
ADD CONSTRAINT fk_articles_status
    FOREIGN KEY (status_id)
    REFERENCES article_statuses(id)
    ON DELETE RESTRICT;


CREATE INDEX idx_articles_author_id
ON articles(author_id);

CREATE INDEX idx_articles_status_id
ON articles(status_id);


ALTER TABLE articles
DROP COLUMN status;