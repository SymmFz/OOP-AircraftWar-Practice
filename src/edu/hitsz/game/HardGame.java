package edu.hitsz.game;

import edu.hitsz.application.ImageManager;
import edu.hitsz.scorerecord.ScoreBoardService;

public class HardGame extends AbstractGame {

    public HardGame(ScoreBoardService scoreBoardService) {
        super(scoreBoardService);
    }

    @Override
    public void configureGame() {
        this.gameDifficulty = GameDifficulty.HARD;
        this.backgroundImage = ImageManager.BACKGROUND_IMAGE5;
        this.baseEnemyMaxNumber = 8;
        this.baseMobEnemyWeight = 5;
        this.baseEliteEnemyWeight = 3;
        this.baseElitePlusEnemyWeight = 2;
        this.baseBossScoreInterval = 6000;
    }

    @Override
    protected double calculateEnemyHpRate(int time) {
        // 每20秒增加0.2倍率，上限为3.0
        return Math.min(3.0, 1.0 + (double) (time / 20000) * 0.2);
    }

    @Override
    protected double calculateEnemyPowerRate(int time) {
        // 每20秒增加0.2倍率，上限为3.0
        return Math.min(3.0, 1.0 + (double) (time / 20000) * 0.2);
    }

    @Override
    protected double calculateBossHpRate(int bossKilledNum) {
        // 每击杀一个Boss，血量倍率增加0.4，上限为4.0
        return Math.min(4.0, 1.0 + bossKilledNum * 0.4);
    }

    @Override
    protected double calculateBossPowerRate(int bossKilledNum) {
        // 每击杀一个Boss，火力倍率增加0.3，上限为3.5
        return Math.min(3.5, 1.0 + bossKilledNum * 0.3);
    }

    @Override
    protected int calculateEnemyMaxNumber(int baseMaxNumber, int score) {
        // 每获得8000分，最大敌机数+1，上限为20
        return Math.min(20, baseMaxNumber + score / 8000);
    }

    @Override
    protected int calculateEliteEnemyWeight(int baseWeight, int time) {
        // 每10秒，精英机权重+1，上限为8
        return Math.min(8, baseWeight + time / 10000);
    }

    @Override
    protected int calculateElitePlusEnemyWeight(int baseWeight, int time) {
        // 每20秒，超级精英机权重+1，上限为6
        return Math.min(6, baseWeight + time / 20000);
    }

    @Override
    protected int calculateBossScoreInterval(int baseInterval, int bossKilledNum) {
        // 每击杀一个Boss，后续Boss出现的间隔增加2000，上限为16000
        return Math.min(16000, baseInterval + bossKilledNum * 1000);
    }
}

