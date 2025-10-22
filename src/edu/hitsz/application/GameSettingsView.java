package edu.hitsz.application;

import edu.hitsz.game.EasyGame;
import edu.hitsz.game.HardGame;
import edu.hitsz.game.NormalGame;
import edu.hitsz.scorerecord.ScoreBoardService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameSettingsView {
    private JPanel mainPanel;
    private JButton easyModeButton;
    private JButton normalModeButton;
    private JButton hardModeButton;
    private JPanel musicPanel;
    private JCheckBox backGroundMusic;
    private JPanel easyButtonPanel;
    private JPanel normalButtonPanel;
    private JPanel hardButtonPanel;
    private JButton returnButton;

    public GameSettingsView(ScoreBoardService scoreBoardService) {

        easyModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (Main.game != null) {
                    Main.cardPanel.remove(Main.game);
                }
                Main.game = new EasyGame(scoreBoardService);
                Main.cardPanel.add(Main.game, Main.GAME_VIEW);
                Main.cardLayout.show(Main.cardPanel, Main.GAME_VIEW);
                Main.game.action();
            }
        });
        normalModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (Main.game != null) {
                    Main.cardPanel.remove(Main.game);
                }
                Main.game = new NormalGame(scoreBoardService);
                Main.cardPanel.add(Main.game, Main.GAME_VIEW);
                Main.cardLayout.show(Main.cardPanel, Main.GAME_VIEW);
                Main.game.action();
            }
        });
        hardModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (Main.game != null) {
                    Main.cardPanel.remove(Main.game);
                }
                Main.game = new HardGame(scoreBoardService);
                Main.cardPanel.add(Main.game, Main.GAME_VIEW);
                Main.cardLayout.show(Main.cardPanel, Main.GAME_VIEW);
                Main.game.action();
            }
        });
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Main.cardLayout.show(Main.cardPanel, Main.START_MENU_VIEW);
            }
        });
    }

    public JPanel getMainPanel() { return mainPanel; }

}
