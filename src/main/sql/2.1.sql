ALTER TABLE ADVERTISEMENT ADD PUBLISHDATE TIMESTAMP(6);

UPDATE ADVERTISEMENT SET PUBLISHDATE = CREATED WHERE PUBLISHDATE IS NULL;

COMMIT;

// Verkar skapas automatiskt av Tage
CREATE TABLE PHOTOSET_REQUEST
   (REQUEST_ID NUMBER(10) NOT NULL, 
	PHOTOS_ID NUMBER(10,0) NOT NULL, 
UNIQUE ("PHOTOS_ID"),
CONSTRAINT "FKPHOSREID" FOREIGN KEY ("REQUEST_ID")
  REFERENCES REQUEST ("ID") ENABLE, 
CONSTRAINT "FKPHOSPHOID" FOREIGN KEY ("PHOTOS_ID")
  REFERENCES PHOTO ("ID") ENABLE);
  
  --Preference i efterlysningar
  baseURI
  /wps/portal/int?uri=gbglnk:intranat.tage
  
  -- Sidinställning
external-js-3
serviceportal/ckeditor-basic-4.4.7/ckeditor.js

-- Testkonto
x1zzxpal

-- Url test
https://testint.goteborg.se/wps/myportal/int/helastaden/tage/