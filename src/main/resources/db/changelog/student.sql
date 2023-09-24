--liquibase formatted sql

--changeset Nemakhov:1
create index student_name_index on student(name)