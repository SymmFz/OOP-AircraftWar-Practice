package edu.hitsz.scorerecord;


import edu.hitsz.game.GameDifficulty;

public interface ScoreRecordObserver {

    void onScoreRecordChanged(GameDifficulty difficulty);
}