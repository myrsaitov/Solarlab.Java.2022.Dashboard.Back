-- Removes all rows from a table or specified partitions of a table, without logging the individual row deletions.
TRUNCATE TABLE category CASCADE;

-- Insert Data into columns
INSERT INTO category (id, created_at, updated_at, name, status) VALUES (1, now(), NULL, 'FromMigrationCategory', 0);

INSERT INTO category (id, created_at, updated_at, name, status, parent_category_id) VALUES (2, now(), NULL, 'Транспорт', 0, (SELECT id from category WHERE id = 0));
INSERT INTO category (id, created_at, updated_at, name, status, parent_category_id) VALUES (3, now(), NULL, 'Автомобили', 0, (SELECT id from category WHERE id = 2));
INSERT INTO category (id, created_at, updated_at, name, status, parent_category_id) VALUES (4, now(), NULL, 'Мотоциклы и мототехника', 0, (SELECT id from category WHERE id = 2));
INSERT INTO category (id, created_at, updated_at, name, status, parent_category_id) VALUES (5, now(), NULL, 'Грузовики и спецтехника', 0, (SELECT id from category WHERE id = 2));

INSERT INTO category (id, created_at, updated_at, name, status, parent_category_id) VALUES (6, now(), NULL, 'Недвижимость', 0, (SELECT id from category WHERE id = 0));
INSERT INTO category (id, created_at, updated_at, name, status, parent_category_id) VALUES (7, now(), NULL, 'Квартиры', 0, (SELECT id from category WHERE id = 6));
INSERT INTO category (id, created_at, updated_at, name, status, parent_category_id) VALUES (8, now(), NULL, 'Комнаты', 0, (SELECT id from category WHERE id = 6));
INSERT INTO category (id, created_at, updated_at, name, status, parent_category_id) VALUES (9, now(), NULL, 'Дома, дачи, коттеджи', 0, (SELECT id from category WHERE id = 6));

INSERT INTO category (id, created_at, updated_at, name, status, parent_category_id) VALUES (10, now(), NULL, 'Электроника', 0, (SELECT id from category WHERE id = 0));
INSERT INTO category (id, created_at, updated_at, name, status, parent_category_id) VALUES (11, now(), NULL, 'Аудио и видео', 0, (SELECT id from category WHERE id = 10));
INSERT INTO category (id, created_at, updated_at, name, status, parent_category_id) VALUES (12, now(), NULL, 'Игры, приставки и программы', 0, (SELECT id from category WHERE id = 10));
INSERT INTO category (id, created_at, updated_at, name, status, parent_category_id) VALUES (13, now(), NULL, 'Настольные компьютеры', 0, (SELECT id from category WHERE id = 10));
INSERT INTO category (id, created_at, updated_at, name, status, parent_category_id) VALUES (14, now(), NULL, 'Ноутбуки', 0, (SELECT id from category WHERE id = 10));
INSERT INTO category (id, created_at, updated_at, name, status, parent_category_id) VALUES (15, now(), NULL, 'Планшеты и электронные книги', 0, (SELECT id from category WHERE id = 10));
INSERT INTO category (id, created_at, updated_at, name, status, parent_category_id) VALUES (16, now(), NULL, 'Телефоны', 0, (SELECT id from category WHERE id = 10));
INSERT INTO category (id, created_at, updated_at, name, status, parent_category_id) VALUES (17, now(), NULL, 'Товары для компьютера', 0, (SELECT id from category WHERE id = 10));
INSERT INTO category (id, created_at, updated_at, name, status, parent_category_id) VALUES (18, now(), NULL, 'Фототехника', 0, (SELECT id from category WHERE id = 10));

INSERT INTO category (id, created_at, updated_at, name, status, parent_category_id) VALUES (19, now(), NULL, 'Искусство', 0, (SELECT id from category WHERE id = 0));
INSERT INTO category (id, created_at, updated_at, name, status, parent_category_id) VALUES (20, now(), NULL, 'Живопись маслом', 0, (SELECT id from category WHERE id = 19));
INSERT INTO category (id, created_at, updated_at, name, status, parent_category_id) VALUES (21, now(), NULL, 'Акварель', 0, (SELECT id from category WHERE id = 19));