package edu.hitsz.scorerecord;

import edu.hitsz.game.GameDifficulty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ScoreBoardService {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final ScoreRecordDao dao;
    private final List<ScoreRecordObserver> observers = new ArrayList<>();

    public ScoreBoardService(ScoreRecordDao dao) {
        this.dao = dao;
    }

    public void addObserver(ScoreRecordObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ScoreRecordObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(GameDifficulty difficulty) {
        for (ScoreRecordObserver observer : observers) {
            observer.onScoreRecordChanged(difficulty);
        }
    }

    public void addRecord(String player, int score, GameDifficulty difficulty) {
        ScoreRecord scoreRecord = new ScoreRecord(player, score, LocalDateTime.now(), difficulty);
        dao.addRecord(scoreRecord);
        notifyObservers(difficulty);
    }

    public void deleteRecordByIndex(int index, GameDifficulty difficulty) {
        dao.deleteRecordByIndex(index, difficulty);
        notifyObservers(difficulty);
    }

    public String[] getScoreBoardColumnName() {
        return new String[]{"名次", "玩家名", "得分", "记录时间"};
    }

    public String[][] getScoreBoardTableData(GameDifficulty difficulty) {
        List<ScoreRecord> records = dao.getAllScoreRecords(difficulty);
        int numberOfRows = records.size();
        String[][] scoreBoardTableData = new String[numberOfRows][];
        int index = 0;
        for (ScoreRecord record : records) {
            String[] recordFields = new String[]{
                    Integer.toString(index + 1),
                    record.getPlayerName(),
                    Integer.toString(record.getScores()),
                    record.getRecordTime().format(TIME_FORMATTER)
            };
            scoreBoardTableData[index] = recordFields;
            index++;
        }
        return scoreBoardTableData;
    }

    public void printSingleScoreRecordByIndex(int index, GameDifficulty difficulty) {
        ScoreRecord record = dao.getSingleScoreRecordByIndex(index, difficulty);
        System.out.println(record);
    }

    public void printAllScoreRecord(GameDifficulty difficulty) {
        int No = 1;
        for (ScoreRecord record : dao.getAllScoreRecords(difficulty)) {
            System.out.println(String.format("第 %d 名：", No) + record);
            No++;
        }
    }

    public void printScoreBoardInConsole(GameDifficulty difficulty) {
        System.out.println("AbstractGame Over!");
        System.out.println("===============================");
        System.out.println("           得分排行榜          ");
        System.out.println("===============================");
        System.out.println("难度：" + difficulty);
        printAllScoreRecord(difficulty);
    }
}
