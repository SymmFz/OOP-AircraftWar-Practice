package edu.hitsz.scorerecord;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScoreRecord {

    private int recordNo;
    private String playerName;
    private int scores;
    private LocalDateTime recordTime;

    public ScoreRecord() {

    }

    public ScoreRecord(String playerName, int scores) {
        this.recordNo = 0;
        this.playerName = playerName;
        this.scores = scores;
        this.recordTime = LocalDateTime.now();
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

    public int getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(int recordNo) {
        this.recordNo = recordNo;
    }

    public LocalDateTime getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(LocalDateTime recordTime) {
        this.recordTime = recordTime;
    }

    @Override
    public String toString() {
        return String.format(
                "第 %d 名：%s，%d，%s",
                getRecordNo(), getPlayerName(), getScores(),
                getRecordTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        );
    }
}
