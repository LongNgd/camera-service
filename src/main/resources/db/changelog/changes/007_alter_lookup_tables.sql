-- liquibase formatted sql

-- changeset longnd:050
ALTER TABLE lookup_value
ALTER COLUMN numeric_value TYPE DOUBLE PRECISION
USING numeric_value::double precision;

-- changeset longnd:051
ALTER TABLE lookup_value
    ADD COLUMN status INT8 DEFAULT 1;

-- changeset longnd:052
ALTER TABLE lookup_type
    ADD COLUMN status INT8 DEFAULT 1;
