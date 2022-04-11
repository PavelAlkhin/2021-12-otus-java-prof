
create table address
(
    id bigint not null,
    street varchar(255),
    client_id int8,
    primary key (id)
);
create table phone
(
    id bigint not null,
    number varchar(255),
    client_id int8,
    primary key (id)
);
alter table client add COLUMN address_id bigint;
alter table address add constraint FK_address_client foreign key (client_id) references client;
alter table client add constraint FK_client_address_id foreign key (address_id) references address;
alter table phone add constraint fk_phone_client_id foreign key (client_id) references client;
