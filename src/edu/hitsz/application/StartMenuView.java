package edu.hitsz.application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenuView {
    private JButton gameSettingsButton;
    private JButton scoreBoardButton;
    private JPanel mainPanel;

    public StartMenuView() {

        gameSettingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.cardLayout.show(Main.cardPanel, Main.GAME_SETTINGS_VIEW);
            }
        });
        scoreBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // 弹出一个窗口询问查看哪个难度的排行榜，难度定义在枚举类型 GameDifficulty 里面
                // 然后给出思路打开对应的排行榜
                Main.cardLayout.show(Main.cardPanel, Main.SCORE_BOARD_VIEW);
            }
        });
    }

    public JPanel getMainPanel() { return mainPanel; }
}
