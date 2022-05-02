-- Use the CREATE SEQUENCE statement to create a sequence, 
-- which is a database object from which multiple users 
-- may generate unique integers. 
-- You can use sequences to automatically generate primary key values.
CREATE SEQUENCE HIBERNATE_SEQUENCE_TAG
    INCREMENT 1
    START 2
    MINVALUE 1
    MAXVALUE 9223372036854775807 -- Long.MAX_VALUE
    CACHE 1
;

-- Create table
CREATE TABLE TAG
(
    id BIGINT NOT NULL, -- Dialect PostgreSQL
    text character varying(255) NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone,
    status integer NOT NULL,
    CONSTRAINT tag_pkey PRIMARY KEY (id)
)
;