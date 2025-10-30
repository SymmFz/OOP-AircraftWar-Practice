package edu.hitsz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StartMenuView {
    private JButton gameSettingsButton;
    private JButton scoreBoardButton;
    private JPanel mainPanel;

    public StartMenuView() {
        if (gameSettingsButton != null) {
            gameSettingsButton.setOpaque(false);
            gameSettingsButton.setContentAreaFilled(false);
            gameSettingsButton.setBorderPainted(false);
            gameSettingsButton.setFocusPainted(false);
            gameSettingsButton.setBackground(new Color(0, 0, 0, 0));
            gameSettingsButton.setText("");
            gameSettingsButton.setForeground(new Color(0,0,0,0));
            scoreBoardButton.setForeground(UIManager.getColor("Button.foreground"));
        }

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

        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    int half = mainPanel.getHeight() / 2;
                    if (e.getY() >= half) {
                        gameSettingsButton.doClick();
                    }
                }
            }
        });
    }

    public JPanel getMainPanel() { return mainPanel; }

    private void createUIComponents() {
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (ImageManager.GAME_BG_IMAGE != null) {
                    g.drawImage(ImageManager.GAME_BG_IMAGE, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
    }
}
