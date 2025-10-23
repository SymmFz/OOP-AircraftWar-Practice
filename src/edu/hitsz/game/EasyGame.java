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
        this.enemyMaxNumber = 3;
        this.mobEnemyWeight = 7;
        this.eliteEnemyWeight = 3;
        this.elitePlusEnemyWeight = 0;
    }
}
