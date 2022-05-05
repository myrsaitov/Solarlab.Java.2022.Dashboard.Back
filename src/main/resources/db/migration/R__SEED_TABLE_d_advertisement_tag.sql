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
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(3),_t(3));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(4),_t(3));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(5),_t(3));
INSERT INTO advertisement_tag (advertisement_id, tag_id) VALUES (_a(6),_t(3));
