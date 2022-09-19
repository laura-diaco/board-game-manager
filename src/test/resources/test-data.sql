BEGIN TRANSACTION;

--Drop Tables
DROP TABLE IF EXISTS board_game, app_user CASCADE;

--Create Tables
CREATE TABLE board_game(
	board_game_id SERIAL,
	title varchar(50) NOT NULL,
	player_minimum int NOT NULL,
	player_maximum int NOT NULL,
	playtime_minimum int NOT NULL,
	playtime_maximum int NOT NULL,
	CONSTRAINT PK_board_game PRIMARY KEY(board_game_id)
);

CREATE TABLE app_user(
	user_id SERIAL,
	user_name varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY(user_id)
);

--Insert Data
INSERT INTO board_game(title, player_minimum, player_maximum, playtime_minimum, playtime_maximum)
	VALUES('Wingspan', 1, 5, 40, 70),
		('Terraforming Mars', 1, 5, 90, 120),
		('7 Wonders Duel', 2, 2, 30, 45),
		('Gloomhaven', 1, 4, 60, 150),
		('Pandemic Legacy: Season 2', 2, 4, 45, 75);

COMMIT;