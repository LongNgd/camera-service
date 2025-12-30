-- liquibase formatted sql

-- changeset longnd:047
ALTER TABLE camera
    ADD COLUMN username VARCHAR(100);

-- changeset longnd:048
ALTER TABLE camera
    ADD COLUMN password VARCHAR(255);
