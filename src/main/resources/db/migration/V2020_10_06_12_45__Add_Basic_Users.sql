INSERT INTO user (id, username, password, enabled) values (1, 'admin', '$2a$10$iy3bvQk2tYA9JbkpRtP6b.3v938KjXg93uTbxnPbmcPBvCZeMLePS', TRUE);
INSERT INTO user_authority (user_id, user_role) values (1, 'ROLE_ADMIN');
INSERT INTO user_authority (user_id, user_role) values (1, 'ROLE_PLAYER');

INSERT INTO user (id, username, password, enabled) values (2, 'user1', '$2a$10$ETwJi0mMPLUcl.8sveiVN.q4dMTJNjGN0kHb1uy4PETRSQ3SRvN2W', TRUE);
INSERT INTO user_authority (user_id, user_role) values (2, 'ROLE_PLAYER');

INSERT INTO user (id, username, password, enabled) values (3, 'user2', '$2a$10$8HoS4nzceFZGex.z004gQOPtl9BFEyiLopy9DIP/cT55LJFlZUrB6', TRUE);
INSERT INTO user_authority (user_id, user_role) values (3, 'ROLE_PLAYER');
