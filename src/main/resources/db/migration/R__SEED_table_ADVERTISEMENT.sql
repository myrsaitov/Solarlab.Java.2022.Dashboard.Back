-- Removes all rows from a table or specified partitions of a table, without logging the individual row deletions.
TRUNCATE TABLE ADVERTISEMENT CASCADE;

-- Insert Data into columns
INSERT INTO ADVERTISEMENT (ID, CREATED_AT, UPDATED_AT, TITLE, BODY, PRICE, STATUS)
    VALUES (1, now(), NULL, 'FromMigrationAdvertisement', 'NULL', 0, 0);