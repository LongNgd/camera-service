-- liquibase formatted sql

-- changeset longnd:043
ALTER TABLE camera
    ADD COLUMN username VARCHAR(100);

-- changeset longnd:044
ALTER TABLE camera
    ADD COLUMN password VARCHAR(255);
