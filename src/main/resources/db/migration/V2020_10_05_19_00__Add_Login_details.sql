create table user(
    username varchar(255) not null primary key,
    password varchar(255) not null
);

create unique index ix_username on user(username);

create table authority(
    username varchar(255) not null,
    name varchar(255) not null,
    constraint fk_user_authority foreign key (username) references user(username)
);

create unique index ix_user_authority on authority(username, name);
