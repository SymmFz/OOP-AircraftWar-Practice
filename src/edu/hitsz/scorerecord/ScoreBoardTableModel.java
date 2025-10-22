package edu.hitsz.scorerecord;

import javax.swing.table.DefaultTableModel;

public class ScoreBoardTableModel extends DefaultTableModel implements ScoreRecordObserver {

    private final ScoreBoardService scoreBoardService;

    public ScoreBoardTableModel(ScoreBoardService scoreBoardService) {
        this.scoreBoardService = scoreBoardService;
        this.scoreBoardService.addObserver(this);
        refreshData();
    }

    private void refreshData() {
        String[] columnNames = scoreBoardService.getScoreBoardColumnName();
        String[][] data = scoreBoardService.getScoreBoardTableData();

        this.setDataVector(data, columnNames);
    }

    @Override
    public void onScoreRecordChanged() {
        refreshData();
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
