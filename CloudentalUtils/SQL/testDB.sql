﻿drop table if exists dentist CASCADE;
CREATE TABLE dentist
(
  id serial NOT NULL,
  username text NOT NULL,
  password text NOT NULL,
  name text NOT NULL,
  surname text NOT NULL,
  CONSTRAINT dentist_pk PRIMARY KEY (id )
);

drop table if exists patient CASCADE;
CREATE TABLE patient
(
	id serial,
	dentistID int references dentist not null,
	name text not null,
	surname text not null,
	comments text,
	created timestamp with time zone default now() not null,
	constraint patient_pk primary key (id)
);

drop table if exists pricelist CASCADE;
CREATE TABLE pricelist
(
  id serial NOT NULL,
  dentistid int references dentist NOT NULL,
  title text NOT NULL,
  description text,
  price numeric NOT NULL,
  CONSTRAINT pricelist_pk PRIMARY KEY (id )
);

drop table if exists discount CASCADE;
CREATE TABLE discount
(
	id serial,
	dentistid int references dentist not null,
	title text not null,
	description text,
	discount decimal not null,
	constraint discount_pk primary key (id)
);

drop table if exists tooth CASCADE;
CREATE TABLE tooth
(
	position integer,
	name text,
	constraint tooth_pk primary key (position)
);

drop table if exists address CASCADE;
CREATE TABLE address
(
	id integer references patient,
	street text,
	number integer,
	postalcode text,
	city text,
	country text,
	adrstype int,
	constraint address_pk primary key (id, adrstype)
);

drop table if exists contactinfo CASCADE;
CREATE TABLE contactinfo
(
	id integer references patient,
	info text,
	infotype int,
	constraint contactinfo_pk primary key (id, infotype)
);
drop table if exists medicalhistory CASCADE;
create table medicalhistory
(
	id integer references patient,
	comments text,
	constraint medicalHistory_pk primary key (id)
);

drop table if exists medicalhistoryentry CASCADE;
create table medicalhistoryentry
(
	id integer references medicalhistory,
	added timestamp with time zone default now(),
	alert integer,
	comments text,
	constraint medicalHistory_entry_pk primary key (id, added)
);

drop table if exists postit CASCADE;
create table postit
(
	id int references dentist,
	postdate timestamp with time zone default now(),
	post text,
	alert integer,
	constraint postit_pk primary key (id, postdate)
);

drop table if exists patienttooth  CASCADE;
create table patienttooth
(
	patientID integer references patient,
	toothID integer references tooth,
	comments text,
	image bytea,
	constraint patienttooth_pk primary key (toothID, patientID)
);


drop table if exists patienthistory CASCADE;
create table patienthistory
(
	patientid integer references patient,
	startdate timestamp with time zone default now(),
	enddate timestamp with time zone default now(),
	comments text,
	constraint patienthistory_pk primary key (patientID)
);


drop table if exists activity CASCADE;
create table activity
(
	id serial, 
	patienthistid integer references patienthistory not null,
	priceable integer references pricelist not null,
	discount integer references discount not null,
	startdate timestamp with time zone default now() not null,
	enddate timestamp with time zone default now(),
	isopen boolean,
	description text,
	price decimal,
	constraint activity_pk primary key (id)
);

drop table if exists visit CASCADE;
create table visit
(
	id serial,
	activityID integer references activity not null,
	visitdate timestamp with time zone default now() not null,
	enddate timestamp with time zone default now(),
	title text not null,
	comments text,
	deposit decimal,
	color integer,
	constraint visit_pk primary key (id)
);

drop table if exists toothhistory CASCADE;
create table toothhistory
(
	id serial,
	visitid integer references visit,
	toothid integer references tooth,
	comments text,
	constraint toothhistory_pk primary key (id)
);

--TEETH
insert into tooth values (1, 'incisor bottom inner left');
insert into tooth values (2, 'incisor bottom inner right');
insert into tooth values (3, 'incisor top inner left');
insert into tooth values (4, 'incisor top inner right');
insert into tooth values (5, 'incisor bottom outer left');
insert into tooth values (6, 'incisor bottom outer right');
insert into tooth values (7, 'incisor top outer right');
insert into tooth values (8, 'incisor top outer right');
insert into tooth values (9, 'canine bottom left');
insert into tooth values (10, 'canine bottom right');
insert into tooth values (11, 'canine top right');
insert into tooth values (12, 'canine top left');
insert into tooth values (13, 'premolar top inner left');
insert into tooth values (14, 'premolar top inner right');
insert into tooth values (15, 'premolar bottom outer left');
insert into tooth values (16, 'premolar bottom outer right');
insert into tooth values (17, 'premolar top outer right');
insert into tooth values (18, 'premolar top outer left');
insert into tooth values (19, 'premolar bottom inner left');
insert into tooth values (20, 'premolar bottom inner right');
insert into tooth values (21, 'molar top inner left');
insert into tooth values (22, 'molar top inner right');
insert into tooth values (23, 'molar bottom outer left');
insert into tooth values (24, 'molar bottom outer right');
insert into tooth values (25, 'molar top outer right');
insert into tooth values (26, 'molar top outer left');
insert into tooth values (27, 'molar bottom inner left');
insert into tooth values (28, 'molar bottom inner right');
insert into tooth values (29, 'wisdom tooth bottom left');
insert into tooth values (30, 'wisdom tooth bottom right');
insert into tooth values (31, 'wisdom tooth top right');
insert into tooth values (32, 'wisdom tooth top left');

--DEFAULTS
--insert into dentist values (-99, 'demo', 'demo', 'demo', 'demo');
--insert into pricelist values (-1, null, 'noprice', 'used internally to represent a non-priced item', 0.0);

