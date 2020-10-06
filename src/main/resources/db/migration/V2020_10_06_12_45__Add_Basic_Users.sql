INSERT INTO user (username, password, enabled) values ('admin', '$2a$10$iy3bvQk2tYA9JbkpRtP6b.3v938KjXg93uTbxnPbmcPBvCZeMLePS', TRUE);
INSERT INTO user_authority (username, user_role) values ('admin', 'ROLE_ADMIN');
INSERT INTO user_authority (username, user_role) values ('admin', 'ROLE_PLAYER');

INSERT INTO user (username, password, enabled) values ('user1', '$2a$10$ETwJi0mMPLUcl.8sveiVN.q4dMTJNjGN0kHb1uy4PETRSQ3SRvN2W', TRUE);
INSERT INTO user_authority (username, user_role) values ('user1', 'ROLE_PLAYER');