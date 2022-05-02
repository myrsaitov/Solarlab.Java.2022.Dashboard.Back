-- Use the CREATE SEQUENCE statement to create a sequence, 
-- which is a database object from which multiple users 
-- may generate unique integers. 
-- You can use sequences to automatically generate primary key values.
CREATE SEQUENCE HIBERNATE_SEQUENCE_ADVERTISEMENT
    INCREMENT 1
    START 2
    MINVALUE 1
    MAXVALUE 9223372036854775807 -- Long.MAX_VALUE
    CACHE 1
;

-- Create table
CREATE TABLE ADVERTISEMENT
(
    id BIGINT NOT NULL, -- Dialect PostgreSQL
    title character varying(255) NOT NULL,
    body character varying(255) NOT NULL,
    price real NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone,
    status integer NOT NULL,
    CONSTRAINT advertisement_pkey PRIMARY KEY (id)
)
;