-- mysql
ALTER TABLE advertisement ADD originalCount INTEGER;
UPDATE advertisement SET originalCount = count;
COMMIT;
-- mssql
ALTER TABLE advertisement ADD originalCount INT;
UPDATE advertisement SET originalCount = count;
COMMIT;