-- liquibase formatted sql

-- changeset longn:039
CREATE TABLE lookup_type (
                             id                  BIGSERIAL PRIMARY KEY,
                             code                VARCHAR(100) NOT NULL,
                             name                VARCHAR(255),
                             description         TEXT,
                             created_user        VARCHAR(100),
                             created_datetime    TIMESTAMP(6),
                             updated_user        VARCHAR(100),
                             updated_datetime    TIMESTAMP(6),
                             CONSTRAINT uk_lookup_type_code UNIQUE (code)
);

-- changeset longn:040
CREATE TABLE lookup_value (
                              id                  BIGSERIAL PRIMARY KEY,
                              lookup_type_id      BIGINT NOT NULL,
                              code                VARCHAR(100) NOT NULL,
                              name                VARCHAR(255),
                              description         TEXT,
                              numeric_value       INT,
                              sort_order          INT,
                              is_active           BOOLEAN DEFAULT TRUE,
                              created_user        VARCHAR(100),
                              created_datetime    TIMESTAMP(6),
                              updated_user        VARCHAR(100),
                              updated_datetime    TIMESTAMP(6),
                              CONSTRAINT fk_lookup_value_type
                                  FOREIGN KEY (lookup_type_id)
                                      REFERENCES lookup_type (id)
                                      ON DELETE RESTRICT,
                              CONSTRAINT uk_lookup_value_code
                                  UNIQUE (lookup_type_id, code)
);