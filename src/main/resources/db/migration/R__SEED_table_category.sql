-- Removes all rows from a table or specified partitions of a table, without logging the individual row deletions.
TRUNCATE TABLE category CASCADE;

-- Insert Data into columns
INSERT INTO category (id, created_at, updated_at, name, status) VALUES (1, now(), now(), 'FromMigrationCategory', 0);