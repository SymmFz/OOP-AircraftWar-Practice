package edu.hitsz.game;

/**
 * Listener interface for receiving high-level game lifecycle events.
 */
public interface GameEventListener {

    /**
     * Called when the current game session ends.
     *
     * @param game     originating game instance
     * @param heroDied true if the hero aircraft was destroyed, false if the game ended for other reasons
     * @param score    final score achieved in the session
     */
    void onGameOver(AbstractGame game, boolean heroDied, int score);

    /**
     * Called whenever a boss enemy is defeated.
     *
     * @param game          originating game instance
     * @param bossKillCount total number of bosses defeated in this session
     * @param score         score at the moment the boss was defeated
     */
    void onBossKilled(AbstractGame game, int bossKillCount, int score);
}
