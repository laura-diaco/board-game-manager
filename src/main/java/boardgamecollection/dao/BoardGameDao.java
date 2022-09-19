package boardgamecollection.dao;

import boardgamecollection.model.BoardGame;

import java.util.List;

public interface BoardGameDao {

    BoardGame getBoardGame(int boardGameId);

    List<BoardGame> getAllBoardGames();

    BoardGame createBoardGame(BoardGame newBoardGame);

    void deleteBoardGame(int boardGameId);

    List<BoardGame> getBoardGamesByPlayerCount(int playerCount);

    List<BoardGame> getBoardGamesByPlaytime(int playtime);

    List<BoardGame> getBoardGamesByTitleLike(String titleLike);

    BoardGame givenListOfBoardGamesPickRandomGame(List<BoardGame> boardGames);
}
