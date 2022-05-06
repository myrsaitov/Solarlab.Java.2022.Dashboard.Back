-- Объявления не засидируются, если нет сидированных категорий!

-- Removes all rows from a table or specified partitions of a table, without logging the individual row deletions.
TRUNCATE TABLE advertisement_tag CASCADE;

-- Функция для привязки объявления к тагу
CREATE OR REPLACE FUNCTION _a(advertisement_id BIGINT)
    RETURNS SETOF BIGINT -- Здесь SET размером 1x1, но он нужен,
                         -- чтобы можно было использовать SELECT
    LANGUAGE plpgsql AS
$func$
BEGIN
    RETURN QUERY
        SELECT id
        FROM  advertisement WHERE id = advertisement_id;
END
$func$;

-- Функция для привязки тага к объявлению
CREATE OR REPLACE FUNCTION _t(tag_id BIGINT)
    RETURNS SETOF BIGINT -- Здесь SET размером 1x1, но он нужен,
-- чтобы можно было использовать SELECT
    LANGUAGE plpgsql AS
$func$
BEGIN
    RETURN QUERY
        SELECT id
        FROM  tag WHERE id = tag_id;
END
$func$;

-- Removes all rows from a table or specified partitions of a table, without logging the individual row deletions.
TRUNCATE TABLE advertisement_tag CASCADE;

-- Insert Data into columns

INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(2),_t(2));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(2),_t(3));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(2),_t(4));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(2),_t(5));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(2),_t(6));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(2),_t(7));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(2),_t(8));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(2),_t(9));

INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(3),_t(2));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(3),_t(3));

INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(4),_t(6));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(4),_t(7));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(4),_t(8));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(4),_t(9));

INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(5),_t(6));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(5),_t(7));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(5),_t(8));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(5),_t(9));

INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(6),_t(2));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(6),_t(3));

INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(7),_t(2));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(7),_t(3));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(7),_t(4));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(7),_t(5));

INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(8),_t(10));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(8),_t(11));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(8),_t(12));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(8),_t(13));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(8),_t(14));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(8),_t(15));

INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(9),_t(10));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(9),_t(11));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(9),_t(13));

INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(10),_t(10));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(10),_t(11));

INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(11),_t(10));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(11),_t(11));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(11),_t(12));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(11),_t(14));

INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(12),_t(10));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(12),_t(11));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(12),_t(12));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(12),_t(14));

INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(13),_t(10));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(13),_t(11));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(13),_t(12));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(13),_t(14));

INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(14),_t(16));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(14),_t(17));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(14),_t(18));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(14),_t(19));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(14),_t(20));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(14),_t(21));

INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(15),_t(16));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(15),_t(17));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(15),_t(18));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(15),_t(19));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(15),_t(20));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(15),_t(21));

INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(16),_t(16));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(16),_t(17));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(16),_t(18));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(16),_t(19));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(16),_t(20));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(16),_t(21));

INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(17),_t(20));

INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(18),_t(20));

INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(19),_t(19));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(19),_t(20));

INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(20),_t(17));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(20),_t(18));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(20),_t(19));

INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(21),_t(19));

INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(22),_t(16));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(22),_t(17));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(22),_t(18));

INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(23),_t(17));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(23),_t(18));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(23),_t(19));

INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(24),_t(16));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(24),_t(18));

INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(25),_t(19));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(25),_t(20));