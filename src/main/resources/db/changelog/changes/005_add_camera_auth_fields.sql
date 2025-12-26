-- liquibase formatted sql

-- changeset longnd:063
ALTER TABLE camera
    ADD COLUMN username VARCHAR(100);

-- changeset longnd:064
ALTER TABLE camera
    ADD COLUMN password VARCHAR(255);
