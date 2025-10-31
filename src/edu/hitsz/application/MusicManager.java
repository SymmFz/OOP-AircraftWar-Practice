package edu.hitsz.application;

public class MusicManager {

    private String bgmMusicFilepath = "src/videos/Vektor - Black Future.wav";
    private static final String BGM_BOSS_MUSIC_FILEPATH = "src/videos/Norther - Midnight Walker.wav";
    private static final String BOMB_EXPLOSION_SOUND_FILEPATH = "src/videos/bomb_explosion.wav";
    private static final String BULLET_HIT_SOUND_FILEPATH = "src/videos/bullet_hit.wav";
    private static final String GET_SUPPLY_SOUND_FILEPATH = "src/videos/get_supply.wav";
    private static final String GAME_OVER_SOUND_FILEPATH = "src/videos/game_over.wav";
    private static final String FAILURE_VOICE1 = "src/videos/failure1.wav";
    private static final String FAILURE_VOICE2 = "src/videos/failure2.wav";
    private static final String FAILURE_VOICE3 = "src/videos/failure3.wav";
    private static final String FAILURE_VOICE4 = "src/videos/failure4.wav";
    private static final String SUCCESS_VOICE1 = "src/videos/success1.wav";
    private static final String SUCCESS_VOICE2 = "src/videos/success2.wav";
    private static final String SUCCESS_VOICE3 = "src/videos/success3.wav";
    private static final String SUCCESS_VOICE4 = "src/videos/success4.wav";
    private static final String SUCCESS_VOICE5 = "src/videos/success5.wav";
    private static final String SUCCESS_VOICE6 = "src/videos/success6.wav";
    private static final String SUCCESS_VOICE7 = "src/videos/success7.wav";
    private static final String SUCCESS_VOICE8 = "src/videos/success8.wav";

    private static final String[] FAILURE_VOICES = {
            FAILURE_VOICE1,
            FAILURE_VOICE2,
            FAILURE_VOICE3,
            FAILURE_VOICE4
    };

    private static final String[] SUCCESS_VOICES = {
            SUCCESS_VOICE1,
            SUCCESS_VOICE2,
            SUCCESS_VOICE3,
            SUCCESS_VOICE4,
            SUCCESS_VOICE5,
            SUCCESS_VOICE6,
            SUCCESS_VOICE7,
            SUCCESS_VOICE8
    };

    private static final MusicManager instance = new MusicManager();

    private MusicManager() {
    }

    public static MusicManager getInstance() {
        return instance;
    }

    private MusicThread bgmMusicThread;
    private boolean isSoundEnabled = true;

    public void setSoundEnabled(boolean enabled) {
        this.isSoundEnabled = enabled;
        if (!enabled) {
            stopBgm();
        }
    }

    public boolean isSoundEnabled() {
        return this.isSoundEnabled;
    }

    // start restart or switch bgm
    public synchronized void switchBgm(String bgmFilePath) {
        if (!isSoundEnabled) {
            return;
        }

        stopBgm();
        bgmMusicThread = new MusicThread(bgmFilePath, true);
        bgmMusicThread.start();
    }

    public synchronized void stopBgm() {
        if (bgmMusicThread != null) {
            bgmMusicThread.softStop();
            bgmMusicThread = null;
        }
    }

    public void playSoundEffect(String soundFilePath) {
        if (!isSoundEnabled) {
            return;
        }
        new MusicThread(soundFilePath, false).start();
    }

    public void changeDefaultBgmFile(String newBgmFilepath) {
        this.bgmMusicFilepath = newBgmFilepath;
    }

    // play Pre-defined music or sound
    public void switchToDefaultBgm() {
        switchBgm(bgmMusicFilepath);
    }

    public void switchToBossBgm() {
        switchBgm(BGM_BOSS_MUSIC_FILEPATH);
    }

    public void playBombExplosionSoundEffect() {
        playSoundEffect(BOMB_EXPLOSION_SOUND_FILEPATH);
    }

    public void playBulletHitSoundEffect() {
        playSoundEffect(BULLET_HIT_SOUND_FILEPATH);
    }

    public void playGetSupplySoundEffect() {
        playSoundEffect(GET_SUPPLY_SOUND_FILEPATH);
    }

    public void playGameOverSoundEffect() {
        playSoundEffect(GAME_OVER_SOUND_FILEPATH);
    }

    public void playRandomFailureVoice() {
        int index = (int) (Math.random() * FAILURE_VOICES.length);
        playSoundEffect(FAILURE_VOICES[index]);
    }

    public void playRandomSuccessVoice() {
        int index = (int) (Math.random() * SUCCESS_VOICES.length);
        playSoundEffect(SUCCESS_VOICES[index]);
    }
}
