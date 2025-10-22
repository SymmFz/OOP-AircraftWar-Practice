package edu.hitsz.scorerecord;

import edu.hitsz.game.GameDifficulty;

import java.util.List;

public interface ScoreRecordDao {

    List<ScoreRecord> getAllScoreRecords(GameDifficulty difficulty);
    ScoreRecord getSingleScoreRecordByIndex(int index, GameDifficulty difficulty);
    void addRecord(ScoreRecord scoreRecord);
    void deleteRecordByIndex(int index, GameDifficulty difficulty);
}
