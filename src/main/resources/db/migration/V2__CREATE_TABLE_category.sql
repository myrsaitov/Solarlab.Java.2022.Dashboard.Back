-- Use the CREATE SEQUENCE statement to create a sequence, 
-- which is a database object from which multiple users 
-- may generate unique integers. 
-- You can use sequences to automatically generate primary key values.
CREATE SEQUENCE hibernate_sequence_category
    INCREMENT 1
    START 100 -- Чтобы не пересекаться с сидированными данными
    MINVALUE 1
    MAXVALUE 9223372036854775807 -- Long.MAX_VALUE
    CACHE 1 -- Для улучшения производительности: это количество элементов,
            -- которые накапливаются в памяти, перед тем как записаться на диск
            -- https://stackoverflow.com/questions/44988294/how-does-the-cache-option-of-create-sequence-work

;

-- Create table
CREATE TABLE category
(
    id BIGINT NOT NULL, -- Dialect PostgreSQL
    name character varying(255) NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone,
    status integer NOT NULL,
    CONSTRAINT category_pkey PRIMARY KEY (id)
);