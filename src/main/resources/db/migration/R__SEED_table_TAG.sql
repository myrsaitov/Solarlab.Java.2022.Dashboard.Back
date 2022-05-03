--Removes all rows from a table or specified partitions of a table, without logging the individual row deletions.
TRUNCATE TABLE TAG CASCADE;

-- Insert Data into columns
INSERT INTO TAG (ID, CREATED_AT, UPDATED_AT, TEXT, STATUS)
    VALUES (1, now(), NULL, 'FromMigrationTag',  0);