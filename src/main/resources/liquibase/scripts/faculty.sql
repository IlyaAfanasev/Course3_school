-- liquibase formatted sql
-- changeset IlyaAfanasev:1
CREATE INDEX faculty_name_color_idx ON faculty (name, color);