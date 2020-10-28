CREATE TABLE game(
	id bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
	game_state varchar(255) NOT NULL,
	board_id bigint
);

CREATE UNIQUE INDEX ix_game_id ON game(id);

CREATE TABLE game_user(
	id bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
	user_id bigint NOT NULL,
	game_id bigint NOT NULL,
	colour varchar(255),
	participation_type varchar(255) NOT NULL,
	CONSTRAINT fk_players__user_game_user_id FOREIGN KEY (user_id) REFERENCES user(id),
	CONSTRAINT fk_players__user_game_game_id FOREIGN KEY (game_id) REFERENCES game(id)
);

CREATE UNIQUE INDEX ix_game_user_id ON game_user(id);
