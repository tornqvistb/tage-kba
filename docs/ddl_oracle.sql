
    drop table Category cascade constraints;

    drop table Person cascade constraints;

    drop table Photo cascade constraints;

    drop table Request cascade constraints;

    drop table Unit cascade constraints;

    drop table Visit cascade constraints;

    drop table advertisement cascade constraints;

    drop table photoset cascade constraints;

    drop sequence hibernate_sequence;

    create table Category (
        id number(10,0) not null,
        title varchar2(255 char) not null,
        parent_id number(10,0),
        primary key (id)
    );

    create table Person (
        id number(10,0) not null,
        email varchar2(255 char) not null,
        name varchar2(255 char) not null,
        phone varchar2(255 char) not null,
        userId varchar2(255 char),
        primary key (id)
    );

    create table Photo (
        id number(10,0) not null,
        creatorUid varchar2(255 char) not null,
        height number(10,0) not null,
        image blob not null,
        mimeType varchar2(255 char),
        thumbnail blob not null,
        title varchar2(255 char),
        width number(10,0) not null,
        primary key (id)
    );

    create table Request (
        id number(10,0) not null,
        created timestamp not null,
        creatorUid varchar2(255 char) not null,
        description varchar2(255 char) not null,
        status number(10,0) not null,
        title varchar2(255 char) not null,
        category_id number(10,0) not null,
        contact_id number(10,0) not null,
        unit_id number(10,0) not null,
        primary key (id),
        unique (contact_id)
    );

    create table Unit (
        id number(10,0) not null,
        name varchar2(255 char),
        primary key (id)
    );

    create table Visit (
        id number(10,0) not null,
        lastVisit timestamp not null,
        userId varchar2(255 char) not null,
        visitCount number(10,0) not null,
        primary key (id)
    );

    create table advertisement (
        id number(10,0) not null,
        created timestamp not null,
        creatorUid varchar2(255 char) not null,
        description varchar2(255 char) not null,
        displayOption number(10,0) not null,
        pickupAddress varchar2(255 char) not null,
        pickupConditions varchar2(255 char),
        status number(10,0) not null,
        title varchar2(255 char) not null,
        booker_id number(10,0),
        category_id number(10,0) not null,
        contact_id number(10,0) not null,
        unit_id number(10,0) not null,
        primary key (id)
    );

    create table photoset (
        advertisement_id number(10,0) not null,
        photos_id number(10,0) not null,
        unique (photos_id)
    );

    alter table Category 
        add constraint FK6DD211E2C2EBFBA 
        foreign key (parent_id) 
        references Category;

    alter table Request 
        add constraint FKA4878A6FFBD5FB26 
        foreign key (unit_id) 
        references Unit;

    alter table Request 
        add constraint FKA4878A6F79C2D1DB 
        foreign key (contact_id) 
        references Person;

    alter table Request 
        add constraint FKA4878A6FC70BAE6 
        foreign key (category_id) 
        references Category;

    alter table advertisement 
        add constraint FKF85DD205EA7030E5 
        foreign key (booker_id) 
        references Person;

    alter table advertisement 
        add constraint FKF85DD205FBD5FB26 
        foreign key (unit_id) 
        references Unit;

    alter table advertisement 
        add constraint FKF85DD20579C2D1DB 
        foreign key (contact_id) 
        references Person;

    alter table advertisement 
        add constraint FKF85DD205C70BAE6 
        foreign key (category_id) 
        references Category;

    alter table photoset 
        add constraint FKB40C78F096F7792E 
        foreign key (advertisement_id) 
        references advertisement;

    alter table photoset 
        add constraint FKB40C78F05D8FA15F 
        foreign key (photos_id) 
        references Photo;

    create sequence hibernate_sequence;
