package edu.hitsz.scorerecord;

public class ScoreBoardService {

    private final ScoreRecordDao scoreRecordDao = new ScoreRecordDaoImpl();

    public void printSingleScoreRecordByNo(int recordNo) {
        ScoreRecord record = scoreRecordDao.getSingleScoreRecordByNo(recordNo);
        System.out.println(record);
    }

    public void printAllScoreRecord() {
        for (ScoreRecord record : scoreRecordDao.getAllScoreRecords()) {
            System.out.println(record);
        }
    }

    public void savePlayerScoreToFile(String player, int score) {
        ScoreRecord scoreRecord = new ScoreRecord(player, score);
        scoreRecordDao.addRecord(scoreRecord);
    }

    public void printScoreBoardInConsole() {
        System.out.println("Game Over!");
        System.out.println("===============================");
        System.out.println("           得分排行榜          ");
        System.out.println("===============================");
        printAllScoreRecord();
    }
}
