CREATE TABLE speciality
(
    id    BIGSERIAL    NOT NULL,
    code  VARCHAR(255) NOT NULL,
    title TEXT         NOT NULL,
    CONSTRAINT pk_speciality PRIMARY KEY (id),
    CONSTRAINT uq_speciality_code UNIQUE (code)
);

CREATE TABLE computer_type
(
    id    BIGSERIAL    NOT NULL,
    code  VARCHAR(255) NOT NULL,
    title TEXT         NOT NULL,
    CONSTRAINT pk_computer_type PRIMARY KEY (id),
    CONSTRAINT uq_computer_type_code UNIQUE (code)
);

CREATE TABLE employee_operation_type
(
    id    SERIAL       NOT NULL,
    code  VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    CONSTRAINT pk_employee_operation_type PRIMARY KEY (id)
);

CREATE TABLE device_operation_type
(
    id    BIGSERIAL    NOT NULL,
    code  VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    CONSTRAINT pk_device_operation_type PRIMARY KEY (id),
    CONSTRAINT uq_device_operation_type_code UNIQUE (code)
);

CREATE TABLE device_group
(
    id    BIGSERIAL    NOT NULL,
    code  VARCHAR(255) NOT NULL,
    title TEXT         NOT NULL,
    CONSTRAINT pk_device_group PRIMARY KEY (id),
    CONSTRAINT uq_device_group_code UNIQUE (code)
);

CREATE TABLE device_type
(
    id              BIGSERIAL    NOT NULL,
    device_group_id BIGINT       NOT NULL,
    code            VARCHAR(255) NOT NULL,
    title           TEXT         NOT NULL,
    CONSTRAINT pk_device_type PRIMARY KEY (id),
    CONSTRAINT fk_device_group FOREIGN KEY (device_group_id) REFERENCES device_group (id),
    CONSTRAINT uq_device_type_code UNIQUE (code)
);


CREATE TABLE computer
(
    id               BIGSERIAL    NOT NULL,
    title            TEXT         NOT NULL,
    cabinet          INT,
    number           VARCHAR(255) NOT NULL,
    ready            BOOLEAN      NOT NULL,
    computer_type_id BIGINT       NOT NULL,
    CONSTRAINT pk_computer PRIMARY KEY (id),
    CONSTRAINT fk_computer_computer_type FOREIGN KEY (computer_type_id) REFERENCES computer_type (id)
);

CREATE TABLE employee
(
    id            BIGSERIAL    NOT NULL,
    first_name    VARCHAR(255) NOT NULL,
    last_name     VARCHAR(255) NOT NULL,
    patronymic    VARCHAR(255),
    speciality_id BIGINT       NOT NULL,
    computer_id   BIGINT,
    CONSTRAINT pk_employee_id PRIMARY KEY (id),
    CONSTRAINT fk_employee_speciality FOREIGN KEY (speciality_id) REFERENCES speciality (id),
    CONSTRAINT fk_employee_computer FOREIGN KEY (computer_id) REFERENCES computer (id),
    CONSTRAINT uq_computer_id UNIQUE (computer_id)
);

CREATE TABLE computer_employee_log
(
    id                         BIGSERIAL    NOT NULL,
    computer_id                BIGINT       NOT NULL,
    employee_id                BIGINT       NOT NULL,
    employee_operation_type_id BIGINT       NOT NULL,
    date                       TIMESTAMP    NOT NULL,
    message                    VARCHAR(255) NOT NULL,
    CONSTRAINT pk_computer_employee PRIMARY KEY (id),
    CONSTRAINT fk_computer_employee_computer FOREIGN KEY (computer_id) REFERENCES computer (id),
    CONSTRAINT fk_computer_employee_employee FOREIGN KEY (employee_id) REFERENCES employee (id),
    CONSTRAINT fk_computer_employee_employee_operation_type FOREIGN KEY (employee_operation_type_id) REFERENCES employee_operation_type (id)
);

CREATE INDEX computer_employee_log_computer_idx ON computer_employee_log (computer_id);
CREATE INDEX computer_employee_log_employee_idx ON computer_employee_log (employee_id);
CREATE INDEX computer_employee_log_employee_operation_type_idx ON computer_employee_log (employee_operation_type_id);

CREATE TABLE device
(
    id             BIGSERIAL    NOT NULL,
    device_type_id BIGINT       NOT NULL,
    title          VARCHAR(255) NOT NULL,
    number         VARCHAR(255) NOT NULL,
    price          INTEGER,
    computer_id    BIGINT,
    CONSTRAINT pk_device_id PRIMARY KEY (id),
    CONSTRAINT fk_device_device_type FOREIGN KEY (device_type_id) REFERENCES device_type (id),
    CONSTRAINT fk_device_computer FOREIGN KEY (computer_id) REFERENCES computer (id)
);

CREATE TABLE computer_device_log
(
    id                       BIGSERIAL    NOT NULL,
    computer_id              BIGINT       NOT NULL,
    device_id                BIGINT       NOT NULL,
    device_operation_type_id BIGINT       NOT NULL,
    date                     TIMESTAMP    NOT NULL,
    message                  VARCHAR(255) NOT NULL,
    CONSTRAINT pk_computer_device_log PRIMARY KEY (id),
    CONSTRAINT fk_computer_device_log_device FOREIGN KEY (device_id) REFERENCES device (id),
    CONSTRAINT fk_computer_device_log_computer FOREIGN KEY (computer_id) REFERENCES computer (id),
    CONSTRAINT fk_computer_device_log_device_operation_type FOREIGN KEY (device_operation_type_id) REFERENCES device_operation_type (id)
);

CREATE INDEX computer_device_log_device_idx ON computer_device_log (device_id);
CREATE INDEX computer_device_log_computer_idx ON computer_device_log (computer_id);
CREATE INDEX computer_device_log_device_operation_type_idx ON computer_device_log (device_operation_type_id);