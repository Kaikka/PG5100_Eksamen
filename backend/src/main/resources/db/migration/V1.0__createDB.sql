create sequence hibernate_sequence start with 1 increment by 1;

/*
Adapted from https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/backend/src/main/resources/db/migration/V1.0__createDB.sql
*/
create table users (username varchar(255) not null, enabled boolean not null, password varchar(255) not null, primary key (username));
create table user_roles (user_username varchar(255) not null, roles varchar(255));


create table movies (id bigint not null, title varchar(255), year int not null, director varchar(255), summary varchar(255), avg_rating double, primary key (id));
create table review (movie_id bigint not null, user_id varchar(255) not null, date_created timestamp, rating integer not null check (rating<=5 AND rating>=1), review_text varchar(2550), primary key (movie_id, user_id));
alter table user_roles add constraint FKs9rxtuttxq2ln7mtp37s4clce foreign key (user_username) references users; /* what does this do? :D */