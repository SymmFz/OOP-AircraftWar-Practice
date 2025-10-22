package edu.hitsz.game;

import edu.hitsz.application.ImageManager;
import edu.hitsz.scorerecord.ScoreBoardService;

public class HardGame extends AbstractGame {

    public HardGame(ScoreBoardService scoreBoardService) {
        super(scoreBoardService);
    }

    @Override
    public void configureGame() {
        this.backgroundImage = ImageManager.BACKGROUND_IMAGE5;
    }
}
