-- Create table
CREATE TABLE category
(
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone,
    status integer NOT NULL,
    CONSTRAINT category_pkey PRIMARY KEY (id)
)
;