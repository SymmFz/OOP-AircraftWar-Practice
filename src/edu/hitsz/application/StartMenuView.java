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
                Main.cardLayout.show(Main.cardPanel, Main.SCORE_BOARD_VIEW);
            }
        });
    }

    public JPanel getMainPanel() { return mainPanel; }
}
