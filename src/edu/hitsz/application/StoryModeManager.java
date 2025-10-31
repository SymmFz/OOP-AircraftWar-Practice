package edu.hitsz.application;

import edu.hitsz.game.AbstractGame;
import edu.hitsz.game.GameEventListener;
import edu.hitsz.game.HardGame;
import edu.hitsz.game.NormalGame;
import edu.hitsz.scorerecord.ScoreBoardService;

import javax.swing.*;
import java.awt.CardLayout;

public class StoryModeManager implements GameEventListener {

    private enum Stage {
        INTRO,
        NORMAL,
        INTERMISSION,
        HARD,
        FINISH
    }

    private final ScoreBoardService scoreBoardService;
    private final JPanel cardPanel;
    private final CardLayout cardLayout;

    private AbstractGame currentGame;
    private Stage currentStage = Stage.INTRO;
    private JPanel currentStoryPanel;

    public StoryModeManager(ScoreBoardService scoreBoardService) {
        this.scoreBoardService = scoreBoardService;
        this.cardPanel = Main.cardPanel;
        this.cardLayout = Main.cardLayout;
    }

    public void startStoryMode() {
        SwingUtilities.invokeLater(() -> showStory(
                "序章",
                "在一座濒临沦陷的天空都市，英雄孤身驾机而行。\n" +
                        "如今的一切灾厄皆因某场隐秘的实验失败所致。" +
                        "\n\n是时候揭开真相了。",
                this::startNormalStage
        ));
    }

    private void startNormalStage() {
        currentStage = Stage.NORMAL;
        launchGame(new NormalGame(scoreBoardService));
    }

    private void startHardStage() {
        currentStage = Stage.HARD;
        launchGame(new HardGame(scoreBoardService));
    }

    private void showStory(String title, String body, Runnable onConfirm) {
        StoryView storyView = new StoryView(title, body, () -> {
            clearStoryPanel();
            if (onConfirm != null) {
                onConfirm.run();
            }
        });
        final AbstractGame gameToRemove = currentGame;

        if (currentStoryPanel != null) {
            cardPanel.remove(gameToRemove);
            cardPanel.revalidate();
            cardPanel.repaint();
        }
        currentStoryPanel = storyView.getMainPanel();
        cardPanel.add(currentStoryPanel, Main.STORY_VIEW);
        cardLayout.show(cardPanel, Main.STORY_VIEW);
    }

    private void launchGame(AbstractGame game) {
        shutdownCurrentGame();

        AbstractGame previousGlobalGame = Main.game;
        if (previousGlobalGame != null && previousGlobalGame != game) {
            cardPanel.remove(previousGlobalGame);
        }

        currentGame = game;
        currentGame.setStoryMode(true);
        currentGame.addGameEventListener(this);
        Main.game = currentGame;

        if (currentStoryPanel != null) {
            cardPanel.remove(currentStoryPanel);
            currentStoryPanel = null;
            cardPanel.revalidate();
            cardPanel.repaint();
        }
        cardPanel.add(currentGame, Main.GAME_VIEW);
        cardLayout.show(cardPanel, Main.GAME_VIEW);

        currentGame.action();
    }

    private void shutdownCurrentGame() {
        if (currentGame == null) {
            return;
        }
        MusicManager.getInstance().stopBgm();
        currentGame.removeGameEventListener(this);
        currentGame.stopGame();

        final AbstractGame gameToRemove = currentGame;
        SwingUtilities.invokeLater(() -> {
            cardPanel.remove(gameToRemove);
            cardPanel.revalidate();
            cardPanel.repaint();
        });
        currentGame = null;
        Main.game = null;
    }

    private void clearStoryPanel() {
        if (currentStoryPanel != null) {
            cardPanel.remove(currentStoryPanel);
            cardPanel.revalidate();
            cardPanel.repaint();
            currentStoryPanel = null;
        }
    }

    @Override
    public void onGameOver(AbstractGame game, boolean heroDied, int score) {
        if (game != currentGame) {
            return;
        }

        SwingUtilities.invokeLater(() -> {
            shutdownCurrentGame();
            cardLayout.show(cardPanel, Main.SCORE_BOARD_VIEW);
        });
    }

    @Override
    public void onBossKilled(AbstractGame game, int bossKillCount, int score) {
        if (game != currentGame) {
            return;
        }

        SwingUtilities.invokeLater(() -> {
            if (currentStage == Stage.NORMAL && bossKillCount >= 1) {
                shutdownCurrentGame();
                currentStage = Stage.INTERMISSION;
                showStory(
                        "危机升级",
                        "英雄成功突破了第一道防线，然而更强的敌人正等待着他。\n" +
                                "下一阶段的敌机将火力全开，稍有不慎便前功尽弃。\n\n准备好迎接炼狱难度吧。",
                        this::startHardStage
                );
            } else if (currentStage == Stage.HARD && bossKillCount >= 1) {
                shutdownCurrentGame();
                currentStage = Stage.FINISH;
                showStory(
                        "终章",
                        "伴随着最终 Boss 的坠落，天空逐渐恢复宁静。\n" +
                                "英雄成功阻止了灾难的蔓延，也看见了清晨的第一缕阳光。\n\n感谢游玩剧情模式！",
                        () -> cardLayout.show(cardPanel, Main.SCORE_BOARD_VIEW)
                );
            }
        });
    }
}
