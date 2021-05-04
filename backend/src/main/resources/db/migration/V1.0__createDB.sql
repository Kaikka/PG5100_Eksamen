create sequence hibernate_sequence start with 1 increment by 1;


create table user_roles (user_username varchar(255) not null, roles varchar(255));
create table users (username varchar(255) not null, enabled boolean not null, password varchar(255) not null, primary key (username));
create table movies (id bigint not null, avg_rating double, director varchar(255), summary varchar(255), title varchar(255), primary key (id));
create table review (movie_id bigint not null, user_id varchar(255) not null, date timestamp, rating integer not null check (rating<=5 AND rating>=1), review_text varchar(997), primary key (movie_id, user_id));

alter table user_roles add constraint FKs9rxtuttxq2ln7mtp37s4clce foreign key (user_username) references users;