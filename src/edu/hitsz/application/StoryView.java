package edu.hitsz.application;

import javax.swing.*;
import java.awt.*;

/**
 * Simple reusable panel for showing story text between stages.
 */
public class StoryView {

    private final JPanel mainPanel;

    public StoryView(String title, String bodyText, Runnable onConfirm) {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JLabel logoLabel = buildLogoLabel();
        if (logoLabel != null) {
            JPanel topPanel = new JPanel(new BorderLayout());
            topPanel.setOpaque(false);
            topPanel.add(logoLabel, BorderLayout.CENTER);
            mainPanel.add(topPanel, BorderLayout.NORTH);
        }

        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));

    JTextArea bodyArea = new JTextArea(bodyText);
        bodyArea.setEditable(false);
        bodyArea.setLineWrap(true);
        bodyArea.setWrapStyleWord(true);
    bodyArea.setOpaque(false);
        bodyArea.setFont(new Font("SansSerif", Font.PLAIN, 18));
        bodyArea.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JScrollPane scrollPane = new JScrollPane(bodyArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JButton confirmButton = new JButton("确定");
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        confirmButton.addActionListener(e -> {
            if (onConfirm != null) {
                onConfirm.run();
            }
        });

        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(scrollPane);
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(confirmButton);
        contentPanel.add(Box.createVerticalStrut(30));

        mainPanel.add(contentPanel, BorderLayout.CENTER);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private JLabel buildLogoLabel() {
        if (ImageManager.LOGO_IMAGE == null) {
            return null;
        }

        double ratio = (double) ImageManager.LOGO_IMAGE.getWidth() / ImageManager.LOGO_IMAGE.getHeight();
        int targetHeight = 220;
        int targetWidth = (int) Math.round(targetHeight * ratio);

        Image scaled = ImageManager.LOGO_IMAGE.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(scaled));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        return label;
    }
}
