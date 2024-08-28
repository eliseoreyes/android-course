package edu.fvtc.blackjackandroidui.models;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {

    //UUID uuid = UUID.fromString("237e9877-e79b-12d4-a765-321741963000");   UUID Usage
    private UUID id;
    private String nickName;
    private String userName;
    private String password;
    private boolean isComputerPlayer;
    private int wins;
    private int score;
    private UUID gameId;
    private Game game;

    public User(){

    }
    public User(UUID id, String nickName, String userName, String password, boolean isComputerPlayer, int wins, int score, UUID gameId, Game game) {
        this.id = id;
        this.nickName = nickName;
        this.userName = userName;
        this.password = password;
        this.isComputerPlayer = isComputerPlayer;
        this.wins = wins;
        this.score = score;
        this.gameId = gameId;
        this.game = game;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isComputerPlayer() {
        return isComputerPlayer;
    }

    public void setComputerPlayer(boolean computerPlayer) {
        isComputerPlayer = computerPlayer;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", isComputerPlayer=" + isComputerPlayer +
                ", wins=" + wins +
                ", score=" + score +
                ", gameId=" + gameId +
                ", game=" + game +
                '}';
    }
}
