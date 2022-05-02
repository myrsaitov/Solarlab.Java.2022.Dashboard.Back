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
    ID BIGINT NOT NULL, -- Dialect PostgreSQL
    TEXT character varying(255) NOT NULL,
    CREATED_AT timestamp without time zone NOT NULL,
    UPDATED_AT timestamp without time zone,
    STATUS integer NOT NULL,
    CONSTRAINT TAG_PKEY PRIMARY KEY (ID)
);