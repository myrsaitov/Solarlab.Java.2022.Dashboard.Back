-- Removes all rows from a table or specified partitions of a table, without logging the individual row deletions.
TRUNCATE TABLE advertisement CASCADE;

-- Insert Data into columns
INSERT INTO advertisement (advertisement_id, created_at, updated_at, title, body, price, status) VALUES (1, now(), NULL, 'FromMigrationAdvertisement', 'NULL', 0, 0);