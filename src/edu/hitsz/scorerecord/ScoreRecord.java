package edu.hitsz.scorerecord;

import edu.hitsz.game.GameDifficulty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScoreRecord {

    private String playerName;
    private int scores;
    private LocalDateTime recordTime;
    private GameDifficulty gameDifficulty;

    public ScoreRecord() {
    }

    public ScoreRecord(String playerName, int scores, LocalDateTime recordTime, GameDifficulty gameDifficulty) {
        this.playerName = playerName;
        this.scores = scores;
        this.recordTime = recordTime;
        this.gameDifficulty = gameDifficulty;
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public LocalDateTime getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(LocalDateTime recordTime) {
        this.recordTime = recordTime;
    }

    public GameDifficulty getGameDifficulty() {
        return gameDifficulty;
    }

    public void setGameDifficulty(GameDifficulty gameDifficulty) {
        this.gameDifficulty = gameDifficulty;
    }

    @Override
    public String toString() {
        return String.format(
                "%s，%d，%s",
                getPlayerName(), getScores(),
                getRecordTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        );
    }

}
