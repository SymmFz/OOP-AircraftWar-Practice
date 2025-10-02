package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {
    private static final int expectedLocationX = Main.WINDOW_WIDTH / 2;
    private static final int expectedLocationY = Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight();

    @BeforeEach
    void setUp() throws Exception {
        HeroAircraft.HeroAircraftHolder.reset();
    }

    @Test
    void getInstance() {
        HeroAircraft heroAircraft1 = HeroAircraft.getInstance();
        HeroAircraft heroAircraft2 = HeroAircraft.getInstance();

        assertSame(heroAircraft1, heroAircraft2);
    }

    @Test
    void getHp() {
        HeroAircraft heroAircraft = HeroAircraft.getInstance();
        assertEquals(100, heroAircraft.getHp());
    }

    @Test
    void getLocationX() {
        HeroAircraft heroAircraft = HeroAircraft.getInstance();
        assertEquals(expectedLocationX, heroAircraft.getLocationX());
    }

    @Test
    void getLocationY() {
        HeroAircraft heroAircraft = HeroAircraft.getInstance();
        assertEquals(expectedLocationY, heroAircraft.getLocationY());
    }

    @Test
    void setLocation() {
        HeroAircraft heroAircraft = HeroAircraft.getInstance();
        heroAircraft.setLocation(0, 0);
        assertAll(
                () -> assertEquals(0, heroAircraft.getLocationX()),
                () -> assertEquals(0, heroAircraft.getLocationY())
        );
    }

    @Test
    void getSpeedY() {
        HeroAircraft heroAircraft = HeroAircraft.getInstance();
        assertEquals(0, heroAircraft.getSpeedY());
    }

    @Test
    void decreaseHp() {
        HeroAircraft heroAircraft = HeroAircraft.getInstance();
        heroAircraft.decreaseHp(50);
        assertEquals(50, heroAircraft.getHp());
    }

    @Test
    void decreaseHpLessThanZero() {
        HeroAircraft heroAircraft = HeroAircraft.getInstance();
        heroAircraft.decreaseHp(120);
        assertEquals(0, heroAircraft.getHp());
    }

    @Test
    void increaseHp() {
        HeroAircraft heroAircraft = HeroAircraft.getInstance();
        heroAircraft.decreaseHp(50);
        heroAircraft.increaseHp(30);
        assertEquals(80, heroAircraft.getHp());
    }

    @Test
    void increaseHpMoreThanHundred() {
        HeroAircraft heroAircraft = HeroAircraft.getInstance();
        heroAircraft.increaseHp(20);
        assertEquals(100, heroAircraft.getHp());
    }

    @Test
    void notValid() {
        HeroAircraft heroAircraft = HeroAircraft.getInstance();
        assertFalse(heroAircraft.notValid());
    }

    @Test
    void vanish() {
        HeroAircraft heroAircraft = HeroAircraft.getInstance();
        heroAircraft.vanish();
        assertTrue(heroAircraft.notValid());
    }

    @Test
    void shoot() {
        HeroAircraft heroAircraft = HeroAircraft.getInstance();

        heroAircraft.setLocation(50, 50);
        int expectedBulletNum = 1;
        int expectedX = heroAircraft.getLocationX();
        int expectedY = heroAircraft.getLocationY() + (-1 * 2);
        int expectedSpeedY = heroAircraft.getSpeedY() + (-1 * 5);
        int expectedBulletPower = 30;

        List<BaseBullet> bullets;
        bullets = heroAircraft.shoot();
        assertNotNull(bullets);
        assertEquals(expectedBulletNum, bullets.size());

        BaseBullet bullet = bullets.get(0);
        assertEquals(expectedX, bullet.getLocationX());
        assertEquals(expectedY, bullet.getLocationY());
        assertEquals(expectedSpeedY, bullet.getSpeedY());
        assertEquals(expectedBulletPower, bullet.getPower());
    }
}