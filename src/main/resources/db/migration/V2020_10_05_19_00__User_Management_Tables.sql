CREATE TABLE user(
    username varchar(255) NOT NULL PRIMARY KEY,
    password varchar(255) NOT NULL,
    enabled boolean NOT NULL
);

CREATE UNIQUE INDEX ix_username on user(username);

CREATE TABLE authority(
    user_role varchar(255) NOT NULL PRIMARY KEY
);

CREATE UNIQUE INDEX ix_authority on authority(user_role);

CREATE TABLE user_authority(
	username varchar(255) NOT NULL,
	user_role varchar(255) NOT NULL,
	PRIMARY KEY (username, user_role),
	CONSTRAINT fk_user_authority_username FOREIGN KEY (username) REFERENCES user(username),
	CONSTRAINT fk_user_authority_user_role FOREIGN KEY (user_role) REFERENCES authority(user_role)
); 


