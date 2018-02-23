
    alter table Category 
        drop 
        foreign key FK6DD211E2C2EBFBA;

    alter table Request 
        drop 
        foreign key FKA4878A6FFBD5FB26;

    alter table Request 
        drop 
        foreign key FKA4878A6F79C2D1DB;

    alter table Request 
        drop 
        foreign key FKA4878A6FC70BAE6;

    alter table advertisement 
        drop 
        foreign key FKF85DD205EA7030E5;

    alter table advertisement 
        drop 
        foreign key FKF85DD205FBD5FB26;

    alter table advertisement 
        drop 
        foreign key FKF85DD20579C2D1DB;

    alter table advertisement 
        drop 
        foreign key FKF85DD205C70BAE6;

    alter table photoset 
        drop 
        foreign key FKB40C78F096F7792E;

    alter table photoset 
        drop 
        foreign key FKB40C78F05D8FA15F;

    alter table photoset_request
        drop 
        foreign key FKPHOSETREQIX;        
                    
        
    alter table photoset_request
        drop 
        foreign key FKPHOSETPHOIX;        
        
    drop table if exists Category;

    drop table if exists Person;

    drop table if exists Photo;

    drop table if exists Request;

    drop table if exists Unit;

    drop table if exists advertisement;

    drop table if exists photoset;
    
    drop table if exists photoset_request;
    
    drop table if exists visit;
    

    create table Category (
        id integer not null auto_increment,
        title varchar(255) not null,
        parent_id integer,
        primary key (id)
    );

    create table Person (
        id integer not null auto_increment,
        email varchar(255) not null,
        name varchar(255) not null,
        phone varchar(255) not null,
        userid varchar(255),
        primary key (id)
    );

    create table Photo (
        id integer not null auto_increment,
        height integer not null,
        image mediumblob not null,
        mimeType varchar(255),
        thumbnail mediumblob not null,
        title varchar(255),
        creatoruid varchar(255) not null,
        width integer not null,
        primary key (id)
    );

    create table Request (
        id integer not null auto_increment,
        created datetime not null,
        creatoruid varchar(255) not null,
        description varchar(1000) not null,
        status integer not null,
        title varchar(255) not null,
        category_id integer not null,
        contact_id integer not null,
        unit_id integer not null,
        primary key (id),
        unique (contact_id)
    );

    create table Unit (
        id integer not null auto_increment,
        name varchar(255),
        primary key (id)
    );

    create table advertisement (
        id integer not null auto_increment,
        created datetime not null,
        creatorUid varchar(255) not null,
        description varchar(1000) not null,
        displayoption integer not null,
        pickupAddress varchar(255) not null,
        pickupConditions varchar(1000),
        status integer not null,
        title varchar(255) not null,
        booker_id integer,
        category_id integer not null,
        contact_id integer not null,
        unit_id integer not null,
        publishdate datetime not null,
        primary key (id)
    );

    create table photoset (
        advertisement_id integer not null,
        photos_id integer not null,
        primary key (photos_id)
    );

    create table photoset_request (
        request_id integer not null,
        photos_id integer not null,
        primary key (photos_id)
    );

    create table visit (
        id integer not null auto_increment,
        lastvisit datetime not null,
        userid varchar(255) not null,
        visitcount integer not null,
        primary key (id)
    );
    
    
    alter table Category 
        add index FK6DD211E2C2EBFBA (parent_id), 
        add constraint FK6DD211E2C2EBFBA 
        foreign key (parent_id) 
        references Category (id);

    alter table Request 
        add index FKA4878A6FFBD5FB26 (unit_id), 
        add constraint FKA4878A6FFBD5FB26 
        foreign key (unit_id) 
        references Unit (id);

    alter table Request 
        add index FKA4878A6F79C2D1DB (contact_id), 
        add constraint FKA4878A6F79C2D1DB 
        foreign key (contact_id) 
        references Person (id);

    alter table Request 
        add index FKA4878A6FC70BAE6 (category_id), 
        add constraint FKA4878A6FC70BAE6 
        foreign key (category_id) 
        references Category (id);

    alter table advertisement 
        add index FKF85DD205EA7030E5 (booker_id), 
        add constraint FKF85DD205EA7030E5 
        foreign key (booker_id) 
        references Person (id);

    alter table advertisement 
        add index FKF85DD205FBD5FB26 (unit_id), 
        add constraint FKF85DD205FBD5FB26 
        foreign key (unit_id) 
        references Unit (id);

    alter table advertisement 
        add index FKF85DD20579C2D1DB (contact_id), 
        add constraint FKF85DD20579C2D1DB 
        foreign key (contact_id) 
        references Person (id);

    alter table advertisement 
        add index FKF85DD205C70BAE6 (category_id), 
        add constraint FKF85DD205C70BAE6 
        foreign key (category_id) 
        references Category (id);

    alter table photoset 
        add index FKB40C78F096F7792E (advertisement_id), 
        add constraint FKB40C78F096F7792E 
        foreign key (advertisement_id) 
        references advertisement (id);

    alter table photoset 
        add index FKB40C78F05D8FA15F (photos_id), 
        add constraint FKB40C78F05D8FA15F 
        foreign key (photos_id) 
        references Photo (id);

    alter table photoset_request 
        add index FKPHOSETREQIX (request_id), 
        add constraint FKPHOSETREQIX 
        foreign key (request_id) 
        references Request (id);

    alter table photoset_request
        add index FKPHOSETPHOIX (photos_id), 
        add constraint FKPHOSETPHOIX 
        foreign key (photos_id) 
        references Photo (id);
