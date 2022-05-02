-- Removes all rows from a table or specified partitions of a table, without logging the individual row deletions.
TRUNCATE TABLE CATEGORY CASCADE;

-- Insert Data into columns
INSERT INTO CATEGORY (ID, CREATED_AT, UPDATED_AT, NAME, STATUS)
    VALUES (1, now(), now(), 'FromMigrationCategory', 0);