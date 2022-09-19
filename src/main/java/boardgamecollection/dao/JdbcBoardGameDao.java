package boardgamecollection.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sql.DataSource;

import boardgamecollection.model.BoardGame;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JdbcBoardGameDao implements BoardGameDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcBoardGameDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public BoardGame getBoardGame(int boardGameId) {
        BoardGame boardGame = new BoardGame();
        String sqlGetBoardGame = "SELECT board_game_id, title, player_minimum, player_maximum, playtime_minimum, playtime_maximum FROM board_game WHERE board_game_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sqlGetBoardGame, boardGameId);
        if (result.next()) {
            boardGame = mapRowToBoardGame(result);
        } else {
            boardGame = null;
        }
        return boardGame;
    }

    @Override
    public List<BoardGame> getAllBoardGames() {
        List<BoardGame> boardGameList = new ArrayList<>();
        String sqlGetBoardGameList = "SELECT board_game_id, title, player_minimum, player_maximum, playtime_minimum, playtime_maximum FROM board_game;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sqlGetBoardGameList);
        while (result.next()) {
            boardGameList.add(mapRowToBoardGame(result));
        }
        return boardGameList;
    }

    @Override
    public BoardGame createBoardGame(BoardGame newBoardGame) {
        String sqlCreateBoardGame = "INSERT INTO board_game(title, player_minimum, player_maximum, playtime_minimum, playtime_maximum) VALUES(?, ?, ?, ?, ?) RETURNING board_game_id;";
        int newBoardGameId = jdbcTemplate.queryForObject(sqlCreateBoardGame, int.class, newBoardGame.getGameTitle(), newBoardGame.getPlayerMinimum(), newBoardGame.getPlayerMaximum(), newBoardGame.getPlayTimeMinimum(), newBoardGame.getPlayTimeMaximum());
        newBoardGame.setId(newBoardGameId);
        return newBoardGame;
    }

    @Override
    public void deleteBoardGame(int boardGameId) {
        String sqlDeleteBoardGame = "DELETE FROM board_game WHERE board_game_id =?;";
        jdbcTemplate.update(sqlDeleteBoardGame, boardGameId);
    }

    @Override
    public List<BoardGame> getBoardGamesByPlayerCount(int playerCount) {
        List<BoardGame> boardGameList = new ArrayList<>();
        String sqlGetBoardGameListByPlayerCount = "SELECT board_game_id, title, player_minimum, player_maximum, playtime_minimum, playtime_maximum FROM board_game WHERE ? BETWEEN player_minimum AND player_maximum;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sqlGetBoardGameListByPlayerCount, playerCount);
        while (result.next()) {
            boardGameList.add(mapRowToBoardGame(result));
        }
        return boardGameList;
    }

    @Override
    public List<BoardGame> getBoardGamesByPlaytime(int playtime) {
        List<BoardGame> boardGameList = new ArrayList<>();
        String sqlGetBoardGameListByPlaytime = "SELECT board_game_id, title, player_minimum, player_maximum, playtime_minimum, playtime_maximum FROM board_game WHERE ? BETWEEN playtime_minimum AND playtime_maximum;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sqlGetBoardGameListByPlaytime, playtime);
        while (result.next()) {
            boardGameList.add(mapRowToBoardGame(result));
        }
        return boardGameList;
    }

    @Override
    public List<BoardGame> getBoardGamesByTitleLike(String titleLike) {
        List<BoardGame> boardGameList = new ArrayList<>();
        String sqlGetBoardGameListByPlayerCount = "SELECT board_game_id, title, player_minimum, player_maximum, playtime_minimum, playtime_maximum FROM board_game WHERE title ILIKE ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sqlGetBoardGameListByPlayerCount, "%" + titleLike + "%");
        while (result.next()) {
            boardGameList.add(mapRowToBoardGame(result));
        }
        return boardGameList;
    }

    @Override
    public BoardGame givenListOfBoardGamesPickRandomGame(List<BoardGame> boardGames) {
        Random random = new Random();
        return boardGames.get(random.nextInt(boardGames.size()));
    }

    private BoardGame mapRowToBoardGame(SqlRowSet result) {
        BoardGame boardGame = new BoardGame();
        boardGame.setId(result.getInt("board_game_id"));
        boardGame.setGameTitle(result.getString("title"));
        boardGame.setPlayerMinimum(result.getInt("player_minimum"));
        boardGame.setPlayerMaximum(result.getInt("player_maximum"));
        boardGame.setPlayTimeMinimum(result.getInt("playtime_minimum"));
        boardGame.setPlayTimeMaximum(result.getInt("playtime_maximum"));
        return boardGame;
    }

}
