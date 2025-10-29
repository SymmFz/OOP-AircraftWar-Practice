package edu.hitsz.game;

import edu.hitsz.application.ImageManager;
import edu.hitsz.scorerecord.ScoreBoardService;

public class EasyGame extends AbstractGame {

    public EasyGame(ScoreBoardService scoreBoardService) {
        super(scoreBoardService);
    }

    @Override
    public void configureGame() {
        this.gameDifficulty = GameDifficulty.EASY;
        this.backgroundImage = ImageManager.BACKGROUND_IMAGE2;
        this.baseEnemyMaxNumber = 3;
        this.baseMobEnemyWeight = 8;
        this.baseEliteEnemyWeight = 2;
        this.baseElitePlusEnemyWeight = 0;
        this.baseBossScoreInterval = Integer.MAX_VALUE;
    }

    @Override
    protected double calculateEnemyHpRate(int time) {
        return 1.0;
    }

    @Override
    protected double calculateEnemyPowerRate(int time) {
        return 1.0;
    }

    @Override
    protected double calculateBossHpRate(int bossKilledNum) {
        return 1.0;
    }

    @Override
    protected double calculateBossPowerRate(int bossKilledNum) {
        return 1.0;
    }

    @Override
    protected int calculateEnemyMaxNumber(int baseMaxNumber, int score) {
        return baseMaxNumber;
    }

    @Override
    protected int calculateEliteEnemyWeight(int baseWeight, int time) {
        return baseWeight;
    }

    @Override
    protected int calculateElitePlusEnemyWeight(int baseWeight, int time) {
        return 0;
    }
}
