CREATE TABLE user(
	id bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    enabled boolean NOT NULL
);

CREATE UNIQUE INDEX ix_username on user(username);

CREATE TABLE authority(
    user_role varchar(255) NOT NULL PRIMARY KEY
);

CREATE UNIQUE INDEX ix_authority on authority(user_role);

CREATE TABLE user_authority(
	user_id bigint NOT NULL,
	user_role varchar(255) NOT NULL,
	PRIMARY KEY (user_id, user_role),
	CONSTRAINT fk_user_authority_user_id FOREIGN KEY (user_id) REFERENCES user(id),
	CONSTRAINT fk_user_authority_user_role FOREIGN KEY (user_role) REFERENCES authority(user_role)
); 


