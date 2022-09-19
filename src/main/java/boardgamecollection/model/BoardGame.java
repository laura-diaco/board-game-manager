package boardgamecollection.model;

public class BoardGame {

    //member variables
    private int id;
    private String gameTitle;
    private int playerMinimum;
    private int playerMaximum;
    private int playTimeMinimum;
    private int playTimeMaximum;

    //constructors
    public BoardGame() {
    }

    public BoardGame(int id, String gameTitle, int playerMinimum, int playerMaximum, int playTimeMinimum, int playTimeMaximum) {
        this.id = id;
        this.gameTitle = gameTitle;
        this.playerMinimum = playerMinimum;
        this.playerMaximum = playerMaximum;
        this.playTimeMinimum = playTimeMinimum;
        this.playTimeMaximum = playTimeMaximum;
    }

    //methods

    //getters & setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public int getPlayerMinimum() {
        return playerMinimum;
    }

    public void setPlayerMinimum(int playerMinimum) {
        this.playerMinimum = playerMinimum;
    }

    public int getPlayerMaximum() {
        return playerMaximum;
    }

    public void setPlayerMaximum(int playerMaximum) {
        this.playerMaximum = playerMaximum;
    }

    public int getPlayTimeMinimum() {
        return playTimeMinimum;
    }

    public void setPlayTimeMinimum(int playTimeMinimum) {
        this.playTimeMinimum = playTimeMinimum;
    }

    public int getPlayTimeMaximum() {
        return playTimeMaximum;
    }

    public void setPlayTimeMaximum(int playTimeMaximum) {
        this.playTimeMaximum = playTimeMaximum;
    }

    @Override
    public String toString() {
        return gameTitle;
    }
}
