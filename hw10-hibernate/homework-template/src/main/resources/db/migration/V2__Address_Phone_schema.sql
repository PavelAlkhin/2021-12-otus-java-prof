create table address
(
    id   bigint not null primary key,
    street varchar(256)
);

create table phone
(
    id   bigint not null primary key,
    numer varchar(256)
);

alter table client add address_id bigint;
alter table phone add constraint fk_phone_client_id foreign key (client_id) references client (id);



