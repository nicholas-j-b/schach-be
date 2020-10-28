-- insert into User (username, password)
-- values ('admin1', '{noop}admin1');
-- 
-- insert into User (username, password)
-- values ('user1', '{noop}user1');
-- 
-- insert into Authority (username, name)
-- values ('admin1', 'ROLE_ADMIN');
-- 
-- insert into Authority (username, name)
-- values ('user1', 'ROLE_PLAYER');

-- INSERT INTO user (id, username, password, enabled) values (1, 'user1', '$2a$10$ETwJi0mMPLUcl.8sveiVN.q4dMTJNjGN0kHb1uy4PETRSQ3SRvN2W', TRUE);

-- SELECT u.username, ua.user_role from user u, user_authority ua where u.username = 'user1';

-- select u.username, ua.user_role from user u join user_authority ua on u.id=ua.user_id where u.username = 'admin';

SELECT u.username, ua.user_role 
FROM user u 
JOIN user_authority ua 
ON u.id=ua.user_id 
WHERE u.username = 'admin';