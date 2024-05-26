create table user_registry(
    user_id bigint primary key,
    username varchar(255) not null,
    password varchar(255) not null
);

create table role_registry(
    role_id bigint primary key,
    role varchar(255) not null
);

create table user_role_registry(
    user_id bigint not null,
    role_id bigint not null,
    primary key(user_id, role_id),
    foreign key(user_id) references user_registry,
    foreign key(role_id) references role_registry
);