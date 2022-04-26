-- Create table
CREATE TABLE tag
(
    id integer NOT NULL,
    text character varying(255) NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    status integer NOT NULL,
    CONSTRAINT tag_pkey PRIMARY KEY (id)
)
;