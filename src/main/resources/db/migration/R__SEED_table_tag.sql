--Removes all rows from a table or specified partitions of a table, without logging the individual row deletions.
TRUNCATE TABLE tag CASCADE;

-- Insert Data
INSERT INTO tag (id, created_at, updated_at, text, status) VALUES (1, now(), now(), 'FromMigrationTag',  0);