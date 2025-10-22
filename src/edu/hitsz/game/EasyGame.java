package edu.hitsz.game;

import edu.hitsz.application.ImageManager;
import edu.hitsz.scorerecord.ScoreBoardService;

public class EasyGame extends AbstractGame {

    public EasyGame(ScoreBoardService scoreBoardService) {
        super(scoreBoardService);
    }

    @Override
    public void configureGame() {
        this.backgroundImage = ImageManager.BACKGROUND_IMAGE2;
    }
}
