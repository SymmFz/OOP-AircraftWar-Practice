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
        this.enemyMaxNumber = 5;
        this.mobEnemyWeight = 5;
        this.eliteEnemyWeight = 3;
        this.elitePlusEnemyWeight = 2;
    }
}
