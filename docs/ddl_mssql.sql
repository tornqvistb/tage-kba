
    create table Category (
        id int not null identity primary key,
        title varchar(255) not null,
        parent_id int FOREIGN KEY REFERENCES Category(id),
    );

	
    create table Person (
        id int not null identity primary key,
        email varchar(255) not null,
        name varchar(255) not null,
        phone varchar(255) not null,
        userid varchar(255),
    );

    create table Photo (
        id int not null identity primary key,
        height int not null,
        image varbinary(max) not null,
        mimeType varchar(255),
        thumbnail varbinary(max) not null,
        title varchar(255),
        creatoruid varchar(255) not null,
        width int not null,
    );

	  create table Unit (
        id int not null identity primary key,
        name varchar(255),
        administration_id varchar(255),
    );
	
	
    create table Request (
        id int not null identity primary key,
        created datetime not null,
        creatoruid varchar(255) not null,
        description varchar(1000) not null,
        status int not null,
        title varchar(255) not null,
        category_id int not null FOREIGN KEY REFERENCES Category (id),
        contact_id int not null FOREIGN KEY REFERENCES Person (id),
        unit_id int not null FOREIGN KEY REFERENCES Unit (id),
        unique (contact_id)
    );
	
	
    create table advertisement (
        id int not null identity primary key,
        created datetime not null,
        creatorUid varchar(255) not null,
        description varchar(1000) not null,
        displayoption int not null,
        pickupAddress varchar(255) not null,
        pickupConditions varchar(1000),
        status int not null,
        title varchar(255) not null,
        booker_id int FOREIGN KEY REFERENCES Person (id),
        category_id int not null FOREIGN KEY REFERENCES Category (id),
        contact_id int not null FOREIGN KEY REFERENCES Person (id),
        unit_id int not null FOREIGN KEY REFERENCES Unit (id),
        publishdate datetime not null,
    );
	
	
    create table photoset (
        advertisement_id int not null FOREIGN KEY REFERENCES advertisement (id),
        photos_id int not null primary key FOREIGN KEY REFERENCES Photo (id),
    );

	
    create table photoset_request (
        request_id int not null FOREIGN KEY REFERENCES Request (id),
        photos_id int not null primary key FOREIGN KEY REFERENCES Photo (id),
    );
	
		
    create table visit (
        id int not null identity primary key,
        lastvisit datetime not null,
        userid varchar(255) not null,
        visitcount int not null,
    );
    
    
CREATE INDEX FK6DD211E2C2EBFBA ON Category (parent_id);

CREATE INDEX FKA4878A6FFBD5FB26 ON Request (unit_id);

CREATE INDEX FKA4878A6F79C2D1DB ON Request (contact_id);

CREATE INDEX FKA4878A6FC70BAE6 ON Request (category_id);

CREATE INDEX FKF85DD205EA7030E5 ON advertisement (booker_id);

CREATE INDEX FKF85DD205FBD5FB26 ON advertisement (unit_id);

CREATE INDEX FKF85DD20579C2D1DB ON advertisement (contact_id);

CREATE INDEX FKF85DD205C70BAE6 ON advertisement (category_id);

CREATE INDEX FKB40C78F096F7792E ON photoset (advertisement_id);

CREATE INDEX FKB40C78F05D8FA15F ON photoset (photos_id);

CREATE INDEX FKPHOSETREQIX ON photoset_request (request_id);

CREATE INDEX FKPHOSETPHOIX ON photoset_request (photos_id);

