CREATE USER advertisement_user WITH password 'advertisement_password';
CREATE SCHEMA advertisement_schema AUTHORIZATION advertisement_user;

-- A schema is a collection of database objects including tables,
-- views, triggers, stored procedures, indexes, etc.
-- A schema is associated with a username which is known
-- as the schema owner, who is the owner of the logically
-- related database objects.
-- https://www.sqlservertutorial.net/sql-server-basics/sql-server-create-schema/