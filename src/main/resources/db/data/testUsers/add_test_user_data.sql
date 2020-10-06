insert into User (username, password)
values ('admin1', '{noop}admin1');

insert into User (username, password)
values ('user1', '{noop}user1');

insert into Authority (username, name)
values ('admin1', 'ROLE_ADMIN');

insert into Authority (username, name)
values ('user1', 'ROLE_PLAYER');
