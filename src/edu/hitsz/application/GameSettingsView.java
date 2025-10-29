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
    private JPanel easyButtonPanel;
    private JPanel normalButtonPanel;
    private JPanel hardButtonPanel;
    private JButton returnButton;
    private JComboBox bgmComboBox;

    public GameSettingsView(ScoreBoardService scoreBoardService) {
        String[] bgmOptions = {
                "[技术激流金属]Vektor - Black Future",
                "默认BGM", "无", "GBC日常的小曲",
                "[技术激流金属]Vektor - LCD (Liquid Crystal Disease)",
                "[前卫死亡金属]Death - Scavenger Of Human Sorrow",
                "[金属]Obscura - Emergent Evolution",
                "[日系流行]ヨルシカ - カトレア",
                "[日系摇滚/二次元]Ave Mujica - 天球(そら)のMúsica",
                "[日系摇滚/二次元]Ave Mujica - KiLLKiSS",
                "[日系摇滚/二次元]結束バンド - 星座になれたら-Anime Ver.-",
                "[日系摇滚/二次元]MyGO - 迷路日々",
                "[融合爵士]Mahavishnu Orchestra - The Noonward Race",
                "[融合爵士]Mahavishnu Orchestra - Dawn"
        };
        bgmComboBox.setModel(new DefaultComboBoxModel<>(bgmOptions));

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

        bgmComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String selectedBgm = (String) bgmComboBox.getSelectedItem();
                if (selectedBgm == null) {
                    return;
                }

                switch (selectedBgm) {
                    case "无":
                        MusicManager.getInstance().changeDefaultBgmFile("src/videos/bgm.wav");
                        MusicManager.getInstance().setSoundEnabled(false);
                        break;
                    case "默认BGM":
                        MusicManager.getInstance().changeDefaultBgmFile("src/videos/bgm.wav");
                        MusicManager.getInstance().setSoundEnabled(true);
                        break;
                    case "GBC日常的小曲":
                        MusicManager.getInstance().changeDefaultBgmFile("src/videos/田中ユウスケ, agehasprings - おしんこ.wav");
                        MusicManager.getInstance().setSoundEnabled(true);
                        break;
                    case "[技术激流金属]Vektor - Black Future":
                        MusicManager.getInstance().changeDefaultBgmFile("src/videos/Vektor - Black Future.wav");
                        MusicManager.getInstance().setSoundEnabled(true);
                        break;
                    case "[技术激流金属]Vektor - LCD (Liquid Crystal Disease)":
                        MusicManager.getInstance()
                                .changeDefaultBgmFile("src/videos/Vektor - LCD (Liquid Crystal Disease).wav");
                        MusicManager.getInstance().setSoundEnabled(true);
                        break;
                    case "[前卫死亡金属]Death - Scavenger Of Human Sorrow":
                        MusicManager.getInstance()
                                .changeDefaultBgmFile("src/videos/Death - Scavenger Of Human Sorrow.wav");
                        MusicManager.getInstance().setSoundEnabled(true);
                        break;
                    case "[金属]Obscura - Emergent Evolution":
                        MusicManager.getInstance().changeDefaultBgmFile("src/videos/Obscura - Emergent Evolution.wav");
                        MusicManager.getInstance().setSoundEnabled(true);
                        break;
                    case "[日系流行]ヨルシカ - カトレア":
                        MusicManager.getInstance().changeDefaultBgmFile("src/videos/ヨルシカ - カトレア.wav");
                        MusicManager.getInstance().setSoundEnabled(true);
                        break;
                    case "[日系摇滚/二次元]Ave Mujica - 天球(そら)のMúsica":
                        MusicManager.getInstance().changeDefaultBgmFile("src/videos/Ave Mujica - 天球(そら)のMúsica.wav");
                        MusicManager.getInstance().setSoundEnabled(true);
                        break;
                    case "[日系摇滚/二次元]Ave Mujica - KiLLKiSS":
                        MusicManager.getInstance().changeDefaultBgmFile("src/videos/Ave Mujica - KiLLKiSS.wav");
                        MusicManager.getInstance().setSoundEnabled(true);
                        break;
                    case "[日系摇滚/二次元]結束バンド - 星座になれたら-Anime Ver.-":
                        MusicManager.getInstance().changeDefaultBgmFile("src/videos/結束バンド - 星座になれたら-Anime Ver.-.wav");
                        MusicManager.getInstance().setSoundEnabled(true);
                        break;
                    case "[日系摇滚/二次元]MyGO - 迷路日々":
                        MusicManager.getInstance().changeDefaultBgmFile("src/videos/MyGO - 迷路日々.wav");
                        MusicManager.getInstance().setSoundEnabled(true);
                        break;
                    case "[融合爵士]Mahavishnu Orchestra - The Noonward Race":
                        MusicManager.getInstance()
                                .changeDefaultBgmFile("src/videos/Mahavishnu Orchestra - The Noonward Race.wav");
                        MusicManager.getInstance().setSoundEnabled(true);
                        break;
                    case "[融合爵士]Mahavishnu Orchestra - Dawn":
                        MusicManager.getInstance()
                                .changeDefaultBgmFile("src/videos/Mahavishnu Orchestra - Dawn.wav");
                        MusicManager.getInstance().setSoundEnabled(true);
                        break;
                    default:
                        MusicManager.getInstance().changeDefaultBgmFile("src/videos/bgm.wav");
                        MusicManager.getInstance().setSoundEnabled(true);
                        break;
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

}
