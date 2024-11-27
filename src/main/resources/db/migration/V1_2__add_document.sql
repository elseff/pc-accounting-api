CREATE TABLE document
(
    id            BIGSERIAL    NOT NULL,
    number        VARCHAR(255) NOT NULL,
    created_at    TIMESTAMP    NOT NULL,
    description   TEXT         NOT NULL,
    document_data BYTEA        NOT NULL,
    computer_id   BIGINT       NOT NULL,
    CONSTRAINT pk_document PRIMARY KEY (id),
    CONSTRAINT fk_document_computer FOREIGN KEY (computer_id) REFERENCES computer (id)
);