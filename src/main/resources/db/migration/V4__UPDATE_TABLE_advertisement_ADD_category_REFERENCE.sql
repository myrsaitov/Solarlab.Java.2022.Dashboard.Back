-- Внешний ключ SQL — это ключ, используемый
--     для объединения двух таблиц. Иногда его также
--     называют ссылочным ключом. Внешний ключ — это
--     столбец или комбинация столбцов, значения
--     которых соответствуют Первичному ключу в
--     другой таблице

-- Add column
ALTER TABLE advertisement ADD COLUMN category_id BIGINT;
-- Add reference
ALTER TABLE advertisement ADD CONSTRAINT advertisement_fkey FOREIGN KEY (category_id) REFERENCES category (category_id);
