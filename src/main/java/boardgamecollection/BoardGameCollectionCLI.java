package boardgamecollection;

import java.util.List;
import java.util.Scanner;

import boardgamecollection.dao.BoardGameDao;
import boardgamecollection.dao.JdbcBoardGameDao;
import boardgamecollection.model.BoardGame;

import boardgamecollection.view.Menu;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class BoardGameCollectionCLI {

    private static final String MAIN_MENU_OPTION_VIEW_COLLECTION = "Current Board Game Collection";
    private static final String MAIN_MENU_OPTION_MODIFY_COLLECTION = "Modify Game Collection";
    private static final String MAIN_MENU_OPTION_FIND_GAME = "Find Game to Play";
    private static final String MAIN_MENU_OPTION_EXIT = "Exit";
    private static final String[] MAIN_MENU_OPTIONS = new String[]{MAIN_MENU_OPTION_VIEW_COLLECTION,
            MAIN_MENU_OPTION_MODIFY_COLLECTION,
            MAIN_MENU_OPTION_FIND_GAME,
            MAIN_MENU_OPTION_EXIT};

    private static final String MENU_OPTION_RETURN_TO_MAIN = "Return to main menu";

    private static final String MOD_MENU_OPTION_CREATE_GAME = "Add Game to Collection";
    private static final String MOD_MENU_DELETE_GAME = "Delete Game From Collection";
    private static final String[] MOD_MENU_OPTIONS = new String[]{MOD_MENU_OPTION_CREATE_GAME,
            MOD_MENU_DELETE_GAME,
            MENU_OPTION_RETURN_TO_MAIN};

    private static final String FIND_MENU_GAME_BY_PLAYER_COUNT = "Show all games for a given player count";
    private static final String PICK_MENU_GAME_BY_PLAYER_COUNT = "Pick a random game to play based on player count";
    private static final String FIND_MENU_GAME_BY_PLAYTIME = "Show all games for a given play time";
    private static final String FIND_MENU_GAME_NAME_LIKE = "Search for game by name";
    private static final String[] FIND_MENU_OPTIONS = new String[]{FIND_MENU_GAME_BY_PLAYER_COUNT,
            PICK_MENU_GAME_BY_PLAYER_COUNT,
            FIND_MENU_GAME_BY_PLAYTIME,
            FIND_MENU_GAME_NAME_LIKE,
            MENU_OPTION_RETURN_TO_MAIN};

    private final Menu menu;
    private final BoardGameDao boardGameDao;

    public static void main(String[] args) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/BoardGameCollection");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");

        BoardGameCollectionCLI application = new BoardGameCollectionCLI(dataSource);
        application.run();
    }

    public BoardGameCollectionCLI(DataSource dataSource) {
        this.menu = new Menu(System.in, System.out);
        boardGameDao = new JdbcBoardGameDao(dataSource);
    }

    private void run() {
        boolean running = true;
        while (running) {
            printHeading("Main Menu");
            String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
            if (choice.equals(MAIN_MENU_OPTION_VIEW_COLLECTION)) {
                handleViewCollection();
            } else if (choice.equals(MAIN_MENU_OPTION_MODIFY_COLLECTION)) {
                handleGameModifications();
            } else if (choice.equals(MAIN_MENU_OPTION_FIND_GAME)) {
                handleFindGames();
            } else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
                running = false;
            }
        }
    }

    private void handleViewCollection() {
        printHeading("Your Collection:");
        List<BoardGame> boardGames = boardGameDao.getAllBoardGames();
        listBoardGames(boardGames);
    }

    private void handleGameModifications() {
        printHeading("Modify Options: ");
        String choice = (String) menu.getChoiceFromOptions(MOD_MENU_OPTIONS);
        if (choice.equals(MOD_MENU_OPTION_CREATE_GAME)) {
            handleCreateBoardGame();
        } else if (choice.equals(MOD_MENU_DELETE_GAME)) {
            handleDeleteBoardGame();
        }
    }

    private void handleCreateBoardGame() {
        printHeading("Add New Game to Collection");
        String newBoardGameTitle = getUserInput("Enter new game name");
        String gamePlayerMin = getUserInput("Enter player minimum");
        String gamePlayerMax = getUserInput("Enter player maximum");
        String gamePlaytimeMin = getUserInput("Enter minimum playtime");
        String gamePlaytimeMax = getUserInput("Enter maximum playtime");
        BoardGame newBoardGame = new BoardGame();
        newBoardGame.setGameTitle(newBoardGameTitle);
        newBoardGame.setPlayerMinimum(Integer.parseInt(gamePlayerMin));
        newBoardGame.setPlayerMaximum(Integer.parseInt(gamePlayerMax));
        newBoardGame.setPlayTimeMinimum(Integer.parseInt(gamePlaytimeMin));
        newBoardGame.setPlayTimeMaximum(Integer.parseInt(gamePlaytimeMax));
        newBoardGame = boardGameDao.createBoardGame(newBoardGame);
        System.out.println("\n*** " + newBoardGame.getGameTitle() + " added ***");
    }

    private void handleDeleteBoardGame() {
        printHeading("Delete Game from Collection");
        boardGameDao.deleteBoardGame(Integer.parseInt(getUserInput("Enter ID of game to delete")));
    }

    private void handleFindGames() {
        printHeading("Find Games in Your Collection");
        String choice = (String) menu.getChoiceFromOptions(FIND_MENU_OPTIONS);
        if (choice.equals(FIND_MENU_GAME_BY_PLAYER_COUNT)) {
            handleFindGamesByPlayerCount();
        } else if (choice.equals(PICK_MENU_GAME_BY_PLAYER_COUNT)) {
            handlePickGameByPlayerCount();
        } else if (choice.equals(FIND_MENU_GAME_BY_PLAYTIME)) {
            handleFindGamesByPlaytime();
        } else if (choice.equals(FIND_MENU_GAME_NAME_LIKE)) {
            handleFindGamesByTitleLike();
        }
    }

    private void handleFindGamesByPlaytime() {
        printHeading("Find games for given playtime");
        List<BoardGame> boardGames = boardGameDao.getBoardGamesByPlaytime(Integer.parseInt(getUserInput("Amount of time to play")));
        listBoardGames(boardGames);
    }

    private void handleFindGamesByPlayerCount() {
        printHeading("Find games for given player count");
        List<BoardGame> boardGames = boardGameDao.getBoardGamesByPlayerCount(Integer.parseInt(getUserInput("Number of players")));
        listBoardGames(boardGames);
    }

    private void handlePickGameByPlayerCount() {
        BoardGame selectedGame = boardGameDao.givenListOfBoardGamesPickRandomGame(boardGameDao.getBoardGamesByPlayerCount(Integer.parseInt(getUserInput("Number of players"))));
        printHeading("Game selected:");
        System.out.println(selectedGame.getGameTitle());
    }

    private void handleFindGamesByTitleLike() {
        printHeading("Search for a game by word(s) in name");
        List<BoardGame> boardGames = boardGameDao.getBoardGamesByTitleLike(getUserInput("Phrase to search for"));
        listBoardGames(boardGames);
    }

    private void listBoardGames(List<BoardGame> boardGames) {
        System.out.println();
        if (boardGames.size() > 0) {
            for (BoardGame boardGame : boardGames) {
                System.out.println(boardGame);
            }
        } else {
            System.out.println("\n*** No results ***");
        }
    }

    private void printHeading(String headingText) {
        System.out.println("\n" + headingText);
        for (int i = 0; i < headingText.length(); i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    private String getUserInput(String prompt) {
        System.out.print(prompt + ": ");
        return new Scanner(System.in).nextLine();
    }
}
