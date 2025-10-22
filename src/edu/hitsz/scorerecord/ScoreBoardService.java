package edu.hitsz.scorerecord;

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

    private void notifyObservers() {
        for (ScoreRecordObserver observer : observers) {
            observer.onScoreRecordChanged();
        }
    }

    public void addRecord(String player, int score) {
        ScoreRecord scoreRecord = new ScoreRecord(player, score);
        dao.addRecord(scoreRecord);
        notifyObservers();
    }

    public void deleteRecordByNo(int No) {
        dao.deleteRecordByNo(No);
        notifyObservers();
    }

    public String[] getScoreBoardColumnName() {
        return new String[]{"名次", "玩家名", "得分", "记录时间"};
    }

    public String[][] getScoreBoardTableData() {
        List<ScoreRecord> records = dao.getAllScoreRecords();
        int numberOfRows = records.size();
        String[][] scoreBoardTableData = new String[numberOfRows][];
        int index = 0;
        for (ScoreRecord record : records) {
            String[] recordFields = new String[]{
                    Integer.toString(record.getRecordNo()),
                    record.getPlayerName(),
                    Integer.toString(record.getScores()),
                    record.getRecordTime().format(TIME_FORMATTER)
            };
            scoreBoardTableData[index] = recordFields;
            index++;
        }
        return scoreBoardTableData;
    }

    public void printSingleScoreRecordByNo(int recordNo) {
        ScoreRecord record = dao.getSingleScoreRecordByNo(recordNo);
        System.out.println(record);
    }

    public void printAllScoreRecord() {
        for (ScoreRecord record : dao.getAllScoreRecords()) {
            System.out.println(record);
        }
    }

    public void printScoreBoardInConsole() {
        System.out.println("AbstractGame Over!");
        System.out.println("===============================");
        System.out.println("           得分排行榜          ");
        System.out.println("===============================");
        printAllScoreRecord();
    }
}
