create table client
(
    id   bigint generated by default as identity,
    name varchar(50) not null,
    primary key (id)
);