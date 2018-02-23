SET IDENTITY_INSERT category ON

Insert into category (ID,TITLE,PARENT_ID) values ('1','Möbler',null);
Insert into category (ID,TITLE,PARENT_ID) values ('120','Inredning',null);
Insert into category (ID,TITLE,PARENT_ID) values ('126','Kontorsmöbler',null);
Insert into category (ID,TITLE,PARENT_ID) values ('136','Elektronik och vitvaror',null);
Insert into category (ID,TITLE,PARENT_ID) values ('139','Övrigt',null);
Insert into category (ID,TITLE,PARENT_ID) values ('121','Konst och utsmyckning','120');
Insert into category (ID,TITLE,PARENT_ID) values ('122','Belysning','120');
Insert into category (ID,TITLE,PARENT_ID) values ('123','Rumsväxter','120');
Insert into category (ID,TITLE,PARENT_ID) values ('124','Mattor och textilier','120');
Insert into category (ID,TITLE,PARENT_ID) values ('138','Ljud och bild','136');
Insert into category (ID,TITLE,PARENT_ID) values ('127','Skrivbord och hurtsar','126');
Insert into category (ID,TITLE,PARENT_ID) values ('128','Kontorsstolar','126');
Insert into category (ID,TITLE,PARENT_ID) values ('129','Hyllor och förvaring','126');
Insert into category (ID,TITLE,PARENT_ID) values ('130','Konferensmöbler','126');
Insert into category (ID,TITLE,PARENT_ID) values ('131','Soffor och fåtöljer','1');
Insert into category (ID,TITLE,PARENT_ID) values ('132','Bord och stolar','1');
Insert into category (ID,TITLE,PARENT_ID) values ('133','Förskola och skola','1');
Insert into category (ID,TITLE,PARENT_ID) values ('134','Hela möblemang','1');
Insert into category (ID,TITLE,PARENT_ID) values ('135','Källsorteringsutrustning','120');
Insert into category (ID,TITLE,PARENT_ID) values ('137','Vitvaror och köksmaskiner','136');
Insert into category (ID,TITLE,PARENT_ID) values ('140','Lekutrustning och leksaker','139');
Insert into category (ID,TITLE,PARENT_ID) values ('141','Verktyg','139');
Insert into category (ID,TITLE,PARENT_ID) values ('142','Husgeråd','139');
Insert into category (ID,TITLE,PARENT_ID) values ('143','Stöldskyddsutrustning','139');
Insert into category (ID,TITLE,PARENT_ID) values ('144','Övrigt','136');
Insert into category (ID,TITLE,PARENT_ID) values ('145','Övrigt','120');
Insert into category (ID,TITLE,PARENT_ID) values ('146','Övrigt','126');
Insert into category (ID,TITLE,PARENT_ID) values ('147','Övrigt','1');
Insert into category (ID,TITLE,PARENT_ID) values ('148','Övrigt','139');

SET IDENTITY_INSERT category OFF

SET IDENTITY_INSERT unit ON

Insert into unit (ID,NAME,ADMINISTRATION_ID) values (1,'AB Familjebostäder','213');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (2,'AB Svenska Bostäder','217');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (3,'AB Stockholmshem','216');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (4,'AB Stokab','212');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (5,'Micasa Fastigheter i Stockholm AB','362');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (6,'Skolfastigheter i Stockholm AB','SISAB','235');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (7,'Stockholm Business Region AB','361');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (8,'Stockholm Globe Arena Fastigheter AB','249');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (9,'Stockholm Vatten AB','248');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (10,'Stockholms Hamn AB','278');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (11,'Stockholms stads Bostadsförmedling AB','291');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (12,'Stockholms stads Parkering AB','228');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (13,'Stockholms Stadsteater AB','251');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (14,'S:t Erik Försäkrings AB','298');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (15,'S:t Erik Livförsäkring AB','385');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (16,'S:t Erik Markutveckling AB','292');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (20,'Finansförvaltningen','101');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (21,'Valnämnden','108');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (22,'Stadsledningskontoret','110');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (23,'KF/KS Kansli','111');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (24,'Socialförvaltningen','113');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (25,'Kulturförvaltningen','115');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (26,'Stockholms Stadsbyggnadskontor','116');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (27,'Utbildningsförvaltningen','117');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (28,'Stadsarkivet 120');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (29,'Äldreförvaltningen','122');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (30,'Överförmyndarnämnden','126');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (31,'Revisionskontoret','131');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (32,'Idrottsförvaltningen','132');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (33,'Kyrkogårdsförvaltningen','168');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (34,'Miljöförvaltningen','169');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (35,'Fastighetskontoret','177');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (36,'Trafikkontoret','181');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (37,'Exploateringskontoret','183');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (38,'Renhållningsförvaltningen','187');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (39,'Serviceförvaltningen','190');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (40,'Arbetsmarnadsförvaltningen','191');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (41,'Rinkeby - Kista sdf','701');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (42,'Spånga - Tensta sdf','703');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (43,'Hässelby - Vällingby sdf','704');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (44,'Bromma sdf','706');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (45,'Kungsholmen sdf','708');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (46,'Norrmalm sdf','709');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (47,'Östermalm sdf','710');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (48,'Södermalm sdf','712');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (49,'Enskede – Årsta - Vantör sdf','714');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (50,'Skarpnäck sdf','715');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (51,'Farsta sdf','718');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (52,'Älvsjö sdf','721');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (53,'Hägersten - Liljeholmen sdf','722');
Insert into unit (ID,NAME,ADMINISTRATION_ID) values (54,'Skärholmen sdf','724');


SET IDENTITY_INSERT unit OFF