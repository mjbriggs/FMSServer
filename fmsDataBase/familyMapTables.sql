drop table if exists Persons;
drop table if exists Users;
drop table if exists authTokens;
drop table if exists AuthTokens;
drop table if exists Events;



create table Persons (
  personID text not null primary key,
  descendent text not null,
  firstName text not null,
  lastName text not null,
  gender text not null,
  father text,
  mother text,
  spouse text
);

create table Users (
  username text not null primary key,
  password text not null,
  email text not null,
  firstName text not null,
  lastName text not null,
  gender text not null,
  personID text not null
);

create table AuthTokens(
  username text not null,
  token text not null primary key
  /*personID text not null*/
);

create table Events(
  eventId text not null primary key,
  descendent text not null,
  personID text not null,
  latitude text not null,
  longitude text not null,
  country text not null,
  city text not null,
  eventType text not null,
  year text not null
);

/*Test database info from example.json*/

insert into Users (username, password, email, firstName, lastName,
gender, personID) values ('sheila', 'parker','sheila@parker.com','Sheila','Parker','f','sheila_parker');
insert into Users (username, password, email, firstName, lastName,
gender, personID) values ('Jim', 'bob','jim@bob.com','Jim','Bob','m','jim_bob');


insert into Persons (personID, descendent, firstName, lastName, gender,
father, mother) values ('sheila_parker', 'sheila','Sheila','Parker','f','Patrick_spencer','sally_sponge');
insert into Persons (personID, descendent, firstName, lastName, gender, spouse) values ('Patrick_spencer', 'sheila','Patrick','Spencer','m','sally_sponge');
insert into Persons (personID, descendent, firstName, lastName, gender, spouse) values ('sally_sponge', 'sheila','sally','sponge','f','Patrick_spencer');

insert into Events (eventId, descendent, personID, latitude, longitude, country,
city, eventType, year) values ('sheila_family_map', 'sheila','sheila_parker', 40.0 , -110.0 ,'United States', 'salt lake city','started family map', 2018);
insert into Events (eventId, descendent, personID, latitude, longitude, country,
city, eventType, year) values ('marriage_Patrick_spencer', 'sheila','Patrick_spencer', 0.0 , 0.0 ,'canada', 'toronto', 'married sally sponge', 1998);
insert into Events (eventId, descendent, personID, latitude, longitude, country,
city, eventType, year) values ('marriage_sally_sponge', 'sheila','sally_sponge', 0.0 , 0.0 ,'canada', 'toronto', 'married patrick Spencer', 1998);

insert into AuthTokens (username, token) values ('sheila', 'sheila token');
insert into AuthTokens (username, token) values ('Jim', 'jim token');

