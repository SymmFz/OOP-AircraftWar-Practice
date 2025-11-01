package edu.hitsz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

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
                if (ImageManager.BACKGROUND_GIF != null && ImageManager.BACKGROUND_GIF.getIconWidth() > 0) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    Map<RenderingHints.Key, Object> hints = new HashMap<>();
                    hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                    hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setRenderingHints(hints);

                    int panelW = getWidth();
                    int panelH = getHeight();
                    int imgW = ImageManager.BACKGROUND_GIF.getIconWidth();
                    int imgH = ImageManager.BACKGROUND_GIF.getIconHeight();

                    // 0.8 for 640x960 to 512x768
                    double scale = 0.77;
                    int offsetX = 0;
                    int offsetY = 24;

                    int newW = (int) (imgW * scale);
                    int newH = (int) (imgH * scale);

                    int x = (panelW - newW) / 2 + offsetX;
                    int y = (panelH - newH) / 2 + offsetY;

                    g2d.drawImage(ImageManager.BACKGROUND_GIF.getImage(), x, y, newW, newH, this);
                    g2d.dispose();
                }
            }
        };
    }
}
