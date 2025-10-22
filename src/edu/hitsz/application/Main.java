package edu.hitsz.application;

import edu.hitsz.game.AbstractGame;
import edu.hitsz.game.GameDifficulty;
import edu.hitsz.scorerecord.ScoreBoardService;
import edu.hitsz.scorerecord.ScoreRecordDao;
import edu.hitsz.scorerecord.ScoreRecordDaoImpl;

import javax.swing.*;
import java.awt.*;

/**
 * 程序入口
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;

    public static final CardLayout cardLayout = new CardLayout(0,0);
    public static final JPanel cardPanel = new JPanel(cardLayout);

    public static final String START_MENU_VIEW = "startMenuView";
    public static final String GAME_SETTINGS_VIEW = "gameSettingsView";
    public static final String SCORE_BOARD_VIEW = "scoreBoardView";
    public static final String GAME_VIEW = "gameView";

    public static AbstractGame game;

    public static void main(String[] args) {

        System.out.println("Hello Aircraft War");

        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(cardPanel);

        ScoreRecordDaoImpl scoreRecordDao = ScoreRecordDaoImpl.getInstance();
        ScoreBoardService scoreBoardService = new ScoreBoardService(scoreRecordDao);

        StartMenuView startMenuView = new StartMenuView();
        GameSettingsView gameSettingsView = new GameSettingsView(scoreBoardService);
        ScoreBoardView scoreBoardView = new ScoreBoardView(scoreBoardService);

        cardPanel.add(startMenuView.getMainPanel(), START_MENU_VIEW);
        cardPanel.add(gameSettingsView.getMainPanel(), GAME_SETTINGS_VIEW);
        cardPanel.add(scoreBoardView.getMainPanel(), SCORE_BOARD_VIEW);

        frame.setVisible(true);
    }
}
