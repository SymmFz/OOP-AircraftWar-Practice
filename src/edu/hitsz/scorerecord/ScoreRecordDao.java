package edu.hitsz.scorerecord;

import java.util.List;

public interface ScoreRecordDao {

    List<ScoreRecord> getAllScoreRecords();
    ScoreRecord getSingleScoreRecordByNo(int recordNo);
    void addRecord(ScoreRecord scoreRecord);
    void deleteRecordByNo(int recordNo);
}
