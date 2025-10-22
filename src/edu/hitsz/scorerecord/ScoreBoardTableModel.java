package edu.hitsz.scorerecord;

import edu.hitsz.game.GameDifficulty;

import javax.swing.table.DefaultTableModel;
import java.util.function.Consumer;

public class ScoreBoardTableModel extends DefaultTableModel implements ScoreRecordObserver {

    private final ScoreBoardService scoreBoardService;
    private GameDifficulty gameDifficulty;

    private Consumer<GameDifficulty> onDifficultyChangeCallback;

    public ScoreBoardTableModel(ScoreBoardService scoreBoardService, GameDifficulty initialDifficulty) {
        this.scoreBoardService = scoreBoardService;
        this.scoreBoardService.addObserver(this);
        refreshData(initialDifficulty);
    }

    public void setOnDifficultyChangeCallback(Consumer<GameDifficulty> callback) {
        this.onDifficultyChangeCallback = callback;
    }

    public void refreshData(GameDifficulty difficulty) {
        String[] columnNames = scoreBoardService.getScoreBoardColumnName();
        String[][] data = scoreBoardService.getScoreBoardTableData(difficulty);
        this.gameDifficulty = difficulty;

        this.setDataVector(data, columnNames);
        if (onDifficultyChangeCallback != null) {
            onDifficultyChangeCallback.accept(difficulty);
        }
    }

    public GameDifficulty getGameDifficulty() { return this.gameDifficulty; }

    @Override
    public void onScoreRecordChanged(GameDifficulty difficulty) {
        refreshData(difficulty);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
