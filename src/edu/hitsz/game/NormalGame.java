package edu.hitsz.game;

import edu.hitsz.application.ImageManager;
import edu.hitsz.scorerecord.ScoreBoardService;

public class NormalGame extends AbstractGame {

    public NormalGame(ScoreBoardService scoreBoardService) {
        super(scoreBoardService);
    }

    @Override
    public void configureGame() {
        this.gameDifficulty = GameDifficulty.NORMAL;
        this.backgroundImage = ImageManager.BACKGROUND_IMAGE3;
        this.baseEnemyMaxNumber = 5;
        this.baseMobEnemyWeight = 6;
        this.baseEliteEnemyWeight = 3;
        this.baseElitePlusEnemyWeight = 1;
        this.baseBossScoreInterval = 18000;
    }

    @Override
    protected double calculateEnemyHpRate(int time) {
        // 每25秒增加0.1倍率，上限为2.5
        return Math.min(2.5, 1.0 + (double) (time / 25000) * 0.1);
    }

    @Override
    protected double calculateEnemyPowerRate(int time) {
        // 每25秒增加0.1倍率，上限为2.5
        return Math.min(2.5, 1.0 + (double) (time / 25000) * 0.1);
    }

    @Override
    protected double calculateBossHpRate(int bossKilledNum) {
        // Boss血量不随击杀数增加
        return 1.0;
    }

    @Override
    protected double calculateBossPowerRate(int bossKilledNum) {
        // Boss火力不随击杀数增加
        return 1.0;
    }

    @Override
    protected int calculateEnemyMaxNumber(int baseMaxNumber, int score) {
        // 每获得10000分，最大敌机数+1，上限为15
        return Math.min(15, baseMaxNumber + score / 10000);
    }

    @Override
    protected int calculateEliteEnemyWeight(int baseWeight, int time) {
        // 每15秒，精英机权重+1，上限为6
        return Math.min(6, baseWeight + time / 15000);
    }

    @Override
    protected int calculateElitePlusEnemyWeight(int baseWeight, int time) {
        // 每30秒，超级精英机权重+1，上限为4
        return Math.min(4, baseWeight + time / 30000);
    }
}