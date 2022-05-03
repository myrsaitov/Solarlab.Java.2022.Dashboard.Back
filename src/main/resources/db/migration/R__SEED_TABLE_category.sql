-- Removes all rows from a table or specified partitions of a table, without logging the individual row deletions.
TRUNCATE TABLE category CASCADE;

-- Insert Data into columns
INSERT INTO category (category_id, created_at, updated_at, name, status) VALUES (1, now(), NULL, 'FromMigrationCategory', 0);

INSERT INTO category (category_id, created_at, updated_at, name, status) VALUES (2, now(), NULL, 'Транспорт', 0);
INSERT INTO category (category_id, created_at, updated_at, name, status) VALUES (3, now(), NULL, 'Автомобили', 0);
INSERT INTO category (category_id, created_at, updated_at, name, status) VALUES (4, now(), NULL, 'Мотоциклы и мототехника', 0);
INSERT INTO category (category_id, created_at, updated_at, name, status) VALUES (5, now(), NULL, 'Грузовики и спецтехника', 0);

INSERT INTO category (category_id, created_at, updated_at, name, status) VALUES (6, now(), NULL, 'Недвижимость', 0);
INSERT INTO category (category_id, created_at, updated_at, name, status) VALUES (7, now(), NULL, 'Квартиры', 0);
INSERT INTO category (category_id, created_at, updated_at, name, status) VALUES (8, now(), NULL, 'Комнаты', 0);
INSERT INTO category (category_id, created_at, updated_at, name, status) VALUES (9, now(), NULL, 'Дома, дачи, коттеджи', 0);

INSERT INTO category (category_id, created_at, updated_at, name, status) VALUES (10, now(), NULL, 'Электроника', 0);
INSERT INTO category (category_id, created_at, updated_at, name, status) VALUES (11, now(), NULL, 'Аудио и видео', 0);
INSERT INTO category (category_id, created_at, updated_at, name, status) VALUES (12, now(), NULL, 'Игры, приставки и программы', 0);
INSERT INTO category (category_id, created_at, updated_at, name, status) VALUES (13, now(), NULL, 'Настольные компьютеры', 0);
INSERT INTO category (category_id, created_at, updated_at, name, status) VALUES (14, now(), NULL, 'Ноутбуки', 0);
INSERT INTO category (category_id, created_at, updated_at, name, status) VALUES (15, now(), NULL, 'Планшеты и электронные книги', 0);
INSERT INTO category (category_id, created_at, updated_at, name, status) VALUES (16, now(), NULL, 'Телефоны', 0);
INSERT INTO category (category_id, created_at, updated_at, name, status) VALUES (17, now(), NULL, 'Товары для компьютера', 0);
INSERT INTO category (category_id, created_at, updated_at, name, status) VALUES (18, now(), NULL, 'Фототехника', 0);

INSERT INTO category (category_id, created_at, updated_at, name, status) VALUES (19, now(), NULL, 'Искусство', 0);
INSERT INTO category (category_id, created_at, updated_at, name, status) VALUES (20, now(), NULL, 'Живопись маслом', 0);
INSERT INTO category (category_id, created_at, updated_at, name, status) VALUES (21, now(), NULL, 'Акварель', 0);