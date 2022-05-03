--Removes all rows from a table or specified partitions of a table, without logging the individual row deletions.
TRUNCATE TABLE tag CASCADE;

-- Insert Data into columns
INSERT INTO tag (tag_id, created_at, updated_at, text, status) VALUES (1, now(), NULL, 'FromMigrationTag',  0);

INSERT INTO tag (tag_id, created_at, updated_at, text, status) VALUES (2, now(), NULL, 'машина',  0);
INSERT INTO tag (tag_id, created_at, updated_at, text, status) VALUES (3, now(), NULL, 'автобус',  0);
INSERT INTO tag (tag_id, created_at, updated_at, text, status) VALUES (4, now(), NULL, 'троллейбус',  0);
INSERT INTO tag (tag_id, created_at, updated_at, text, status) VALUES (5, now(), NULL, 'мотоцикл',  0);
INSERT INTO tag (tag_id, created_at, updated_at, text, status) VALUES (6, now(), NULL, 'велосипед',  0);
INSERT INTO tag (tag_id, created_at, updated_at, text, status) VALUES (7, now(), NULL, 'самокат',  0);
INSERT INTO tag (tag_id, created_at, updated_at, text, status) VALUES (8, now(), NULL, 'мотороллер',  0);
INSERT INTO tag (tag_id, created_at, updated_at, text, status) VALUES (9, now(), NULL, 'телефон',  0);
INSERT INTO tag (tag_id, created_at, updated_at, text, status) VALUES (10, now(), NULL, 'планшет',  0);
INSERT INTO tag (tag_id, created_at, updated_at, text, status) VALUES (11, now(), NULL, 'смартфон',  0);
INSERT INTO tag (tag_id, created_at, updated_at, text, status) VALUES (12, now(), NULL, 'ноутбук',  0);