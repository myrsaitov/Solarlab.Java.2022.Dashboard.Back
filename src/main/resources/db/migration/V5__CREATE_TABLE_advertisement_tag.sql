-- Create table
CREATE TABLE advertisement_tag (
    advertisement_id BIGINT REFERENCES advertisement (id) ON UPDATE CASCADE ON DELETE CASCADE,
    tag_id BIGINT REFERENCES tag (id) ON UPDATE CASCADE,
    CONSTRAINT advertisement_tag_pkey PRIMARY KEY (advertisement_id, tag_id)  -- explicit pk
);