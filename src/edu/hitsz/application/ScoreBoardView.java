package edu.hitsz.application;

import edu.hitsz.game.GameDifficulty;
import edu.hitsz.scorerecord.ScoreBoardService;
import edu.hitsz.scorerecord.ScoreBoardTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScoreBoardView {
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JPanel topPanel;
    private JScrollPane tableScrollPanel;
    private JLabel headerLabel;
    private JTable scoreTable;
    private JButton deleteButton;
    private JButton returnButton;
    private JLabel gameDifficultyField;

    public ScoreBoardView(ScoreBoardService scoreBoardService) {

        ScoreBoardTableModel model = new ScoreBoardTableModel(scoreBoardService, GameDifficulty.NORMAL);
        model.setOnDifficultyChangeCallback(
                newDifficulty -> {
                    gameDifficultyField.setText("难度：" + newDifficulty.name());
                }
        );
        scoreBoardService.addObserver(model);

        scoreTable.setModel(model);
        model.refreshData(GameDifficulty.NORMAL);
        tableScrollPanel.setViewportView(scoreTable);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = scoreTable.getSelectedRow();

                if (row == -1) {
                    JOptionPane.showMessageDialog(deleteButton, "请先选择要删除的记录！");
                    return;
                }

                int result = JOptionPane.showConfirmDialog(deleteButton,
                        "是否确定中删除？");
                if (JOptionPane.YES_OPTION == result) {
                    model.removeRow(row);
                    scoreBoardService.deleteRecordByIndex(row, model.getGameDifficulty());
                }
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.cardLayout.show(Main.cardPanel, Main.START_MENU_VIEW);
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

}
