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
        this.enemyMaxNumber = 8;
        this.mobEnemyWeight = 4;
        this.eliteEnemyWeight = 4;
        this.elitePlusEnemyWeight = 2;
    }
}
