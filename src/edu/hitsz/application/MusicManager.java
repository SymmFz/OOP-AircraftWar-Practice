package edu.hitsz.application;

import edu.hitsz.application.MusicThread;

public class MusicManager {

    private static final String BGM_MUSIC_FILEPATH = "src/videos/bgm.wav";
    private static final String BGM_BOSS_MUSIC_FILEPATH = "src/videos/bgm_boss.wav";
    private static final String BOMB_EXPLOSION_SOUND_FILEPATH = "src/videos/bomb_explosion.wav";
    private static final String BULLET_HIT_SOUND_FILEPATH = "src/videos/bullet_hit.wav";
    private static final String GET_SUPPLY_SOUND_FILEPATH = "src/videos/get_supply.wav";
    private static final String GAME_OVER_SOUND_FILEPATH = "src/videos/game_over.wav";

    private static  final MusicManager instance = new MusicManager();
    private MusicManager() {}
    public static MusicManager getInstance() { return instance; }

    private MusicThread bgmMusicThread;
    private boolean isSoundEnabled = true;

    public void setSoundEnabled(boolean enabled) {
        this.isSoundEnabled = enabled;
        if (!enabled) {
            stopBgm();
        }
    }

    public boolean isSoundEnabled() { return this.isSoundEnabled; }

    // start restart or switch bgm
    public synchronized void playBgm(String bgmFilePath) {
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

    // play Pre-defined music or sound
    public void playDefaultBgm() {
        playBgm(BGM_MUSIC_FILEPATH);
    }

    public void playBossBgm() {
        playBgm(BGM_BOSS_MUSIC_FILEPATH);
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
}
