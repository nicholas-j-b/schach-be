CREATE TABLE board_state(
	id bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
	white_status_dto MEDIUMBLOB NOT NULL,
	black_status_dto MEDIUMBLOB NOT NULL,
	turn varchar(255) NOT NULL,
	history MEDIUMBLOB NOT NULL
);

CREATE UNIQUE INDEX ix_board_state_id ON board_state(id);

CREATE TABLE game_starting_position(
	id bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
	creator_username varchar(255) NOT NULL,
	position_name varchar(255) NOT NULL,
	board_state_id bigInt NOT NULL,
	CONSTRAINT fk_game_starting_position__board_state FOREIGN KEY (board_state_id) REFERENCES board_state(id),
	CONSTRAINT fk_game_starting_position__creator_username FOREIGN KEY (creator_username) REFERENCES user(username),
	UNIQUE KEY uk_creator_username_position_name (creator_username, position_name)
);

CREATE UNIQUE INDEX ix_game_starting_position_id ON game_starting_position(id);

