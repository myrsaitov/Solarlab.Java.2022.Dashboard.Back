--Removes all rows from a table or specified partitions of a table, without logging the individual row deletions.
TRUNCATE TABLE tag CASCADE;

-- Insert Data into columns
INSERT INTO tag (id, created_at, updated_at, text, status) VALUES (1, now(), NULL, 'FromMigrationTag',  0);

-- Транспорт
INSERT INTO tag (id, created_at, updated_at, text, status) VALUES (2, now(), NULL, 'машины',  0);
INSERT INTO tag (id, created_at, updated_at, text, status) VALUES (3, now(), NULL, 'автомобили',  0);
INSERT INTO tag (id, created_at, updated_at, text, status) VALUES (4, now(), NULL, 'автобусы',  0);
INSERT INTO tag (id, created_at, updated_at, text, status) VALUES (5, now(), NULL, 'троллейбусы',  0);
INSERT INTO tag (id, created_at, updated_at, text, status) VALUES (6, now(), NULL, 'мотоциклы',  0);
INSERT INTO tag (id, created_at, updated_at, text, status) VALUES (7, now(), NULL, 'велосипеды',  0);
INSERT INTO tag (id, created_at, updated_at, text, status) VALUES (8, now(), NULL, 'самокаты',  0);
INSERT INTO tag (id, created_at, updated_at, text, status) VALUES (9, now(), NULL, 'мотороллеры',  0);

-- Недвижимость
INSERT INTO tag (id, created_at, updated_at, text, status) VALUES (10, now(), NULL, 'комнаты',  0);
INSERT INTO tag (id, created_at, updated_at, text, status) VALUES (11, now(), NULL, 'квартиры',  0);
INSERT INTO tag (id, created_at, updated_at, text, status) VALUES (12, now(), NULL, 'котеджи',  0);
INSERT INTO tag (id, created_at, updated_at, text, status) VALUES (13, now(), NULL, 'мансарды',  0);
INSERT INTO tag (id, created_at, updated_at, text, status) VALUES (14, now(), NULL, 'сараи',  0);
INSERT INTO tag (id, created_at, updated_at, text, status) VALUES (15, now(), NULL, 'склады',  0);

-- Электроника
INSERT INTO tag (id, created_at, updated_at, text, status) VALUES (16, now(), NULL, 'телефон',  0);
INSERT INTO tag (id, created_at, updated_at, text, status) VALUES (17, now(), NULL, 'планшет',  0);
INSERT INTO tag (id, created_at, updated_at, text, status) VALUES (18, now(), NULL, 'смартфон',  0);
INSERT INTO tag (id, created_at, updated_at, text, status) VALUES (19, now(), NULL, 'ноутбук',  0);
INSERT INTO tag (id, created_at, updated_at, text, status) VALUES (20, now(), NULL, 'приставка',  0);
INSERT INTO tag (id, created_at, updated_at, text, status) VALUES (21, now(), NULL, 'рация',  0);

-- Искусство
INSERT INTO tag (id, created_at, updated_at, text, status) VALUES (22, now(), NULL, 'Айвазовский',  0);
INSERT INTO tag (id, created_at, updated_at, text, status) VALUES (23, now(), NULL, 'Левитан',  0);
INSERT INTO tag (id, created_at, updated_at, text, status) VALUES (24, now(), NULL, 'Поленов',  0);
INSERT INTO tag (id, created_at, updated_at, text, status) VALUES (25, now(), NULL, 'кисти',  0);
INSERT INTO tag (id, created_at, updated_at, text, status) VALUES (26, now(), NULL, 'краски',  0);
INSERT INTO tag (id, created_at, updated_at, text, status) VALUES (27, now(), NULL, 'мольберты',  0);
INSERT INTO tag (id, created_at, updated_at, text, status) VALUES (28, now(), NULL, 'мастихины',  0);
