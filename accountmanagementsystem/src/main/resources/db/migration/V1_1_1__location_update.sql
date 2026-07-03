create table IF NOT EXISTS locations(
    id varchar(50),
    area varchar(30) not null,
    description text null,
    created_at timestamp not null,
    updated_at timestamp null,
    primary key(id)
);