ALTER TABLE user
	ADD COLUMN game_user_id bigint,
	ADD CONSTRAINT fk_user__user_user_game FOREIGN KEY (game_user_id) REFERENCES game_user(id);
