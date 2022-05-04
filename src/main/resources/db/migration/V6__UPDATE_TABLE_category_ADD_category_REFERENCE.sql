-- Внешний ключ SQL — это ключ, используемый
--     для объединения двух таблиц. Иногда его также
--     называют ссылочным ключом. Внешний ключ — это
--     столбец или комбинация столбцов, значения
--     которых соответствуют Первичному ключу в
--     другой таблице

-- Add column
ALTER TABLE category ADD COLUMN parent_category_id BIGINT;
-- Add reference
ALTER TABLE category ADD CONSTRAINT parent_category_fkey FOREIGN KEY (parent_category_id) REFERENCES category (id);
