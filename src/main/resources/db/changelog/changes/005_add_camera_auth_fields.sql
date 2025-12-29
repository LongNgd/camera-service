-- liquibase formatted sql

-- changeset longnd:055
ALTER TABLE camera
    ADD COLUMN username VARCHAR(100);

-- changeset longnd:056
ALTER TABLE camera
    ADD COLUMN password VARCHAR(255);
