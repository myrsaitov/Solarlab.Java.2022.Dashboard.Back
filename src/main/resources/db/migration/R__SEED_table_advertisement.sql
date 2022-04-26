-- Removes all rows from a table or specified partitions of a table, without logging the individual row deletions.
TRUNCATE TABLE advertisement CASCADE;

-- Insert Data into columns
INSERT INTO advertisement (id, created_at, updated_at, title, body, price, status) VALUES (1, now(), now(), 'FromMigrationAdvertisement', 'FromMigrationAdvertisement', 0, 0);