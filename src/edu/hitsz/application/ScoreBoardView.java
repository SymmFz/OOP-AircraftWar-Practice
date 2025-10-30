package edu.hitsz.application;

import edu.hitsz.game.GameDifficulty;
import edu.hitsz.scorerecord.ScoreBoardService;
import edu.hitsz.scorerecord.ScoreBoardTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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
    private JButton gameDifficultySwitchButton;

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

        gameDifficultySwitchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                GameDifficulty[] difficulties = GameDifficulty.values();

                GameDifficulty selectedDifficulty = (GameDifficulty) JOptionPane.showInputDialog(
                        mainPanel,
                        "请选择要查看的排行榜难度：",
                        "切换难度",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        difficulties,
                        model.getGameDifficulty()
                );

                if (selectedDifficulty != null) {
                    model.refreshData(selectedDifficulty);
                }
            }
        });

        setupHeaderImage();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void setupHeaderImage() {
        if (ImageManager.LOGO_IMAGE == null || topPanel == null || headerLabel == null) {
            return;
        }

        Runnable updater = this::updateHeaderImage;
        topPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updater.run();
            }
        });
        SwingUtilities.invokeLater(updater);
    }

    private void updateHeaderImage() {
        if (ImageManager.LOGO_IMAGE == null) return;

        int panelH = Math.max(0, topPanel.getHeight());
        int targetH = panelH > 0 ? Math.min(panelH - 5, 280) : 100;
        if (targetH <= 0) targetH = 100;

        double ratio = (double) ImageManager.LOGO_IMAGE.getWidth() / ImageManager.LOGO_IMAGE.getHeight();
        int targetW = (int) Math.round(targetH * ratio);

        Image scaled = ImageManager.LOGO_IMAGE.getScaledInstance(targetW, targetH, Image.SCALE_SMOOTH);
        headerLabel.setIcon(new ImageIcon(scaled));
        headerLabel.setText("");
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

}
