-- mysql
ALTER TABLE advertisement ADD originalCount INTEGER;
UPDATE advertisement SET originalCount = count;
ALTER TABLE advertisement ADD expireType INTEGER;
UPDATE advertisement SET expireType = 0;
ALTER TABLE advertisement ADD expireDate DATETIME;
COMMIT;
-- mssql
ALTER TABLE advertisement ADD originalCount INT;
UPDATE advertisement SET originalCount = count;
ALTER TABLE advertisement ADD expireType INT;
UPDATE advertisement SET expireType = 0;
ALTER TABLE advertisement ADD expireDate DATETIME;
COMMIT;