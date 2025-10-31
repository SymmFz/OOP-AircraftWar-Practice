package edu.hitsz.application;

import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.ElitePlusEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.bullet.*;
import edu.hitsz.item.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 综合管理图片的加载，访问
 * 提供图片的静态访问方法
 *
 * @author hitsz
 */
public class ImageManager {

    /**
     * 类名-图片 映射，存储各基类的图片 <br>
     * 可使用 CLASSNAME_IMAGE_MAP.get( obj.getClass().getName() ) 获得 obj 所属基类对应的图片
     */
    private static final Map<String, BufferedImage> CLASSNAME_IMAGE_MAP = new HashMap<>();

    public static BufferedImage BACKGROUND_IMAGE;
    public static BufferedImage BACKGROUND_IMAGE2;
    public static BufferedImage BACKGROUND_IMAGE3;
    public static BufferedImage BACKGROUND_IMAGE4;
    public static BufferedImage BACKGROUND_IMAGE5;
    public static BufferedImage HERO_IMAGE;
    public static BufferedImage HERO_BULLET_IMAGE;
    public static BufferedImage ENEMY_BULLET_IMAGE;
    public static BufferedImage HERO_DARTS_BULLET_IMAGE;
    public static BufferedImage HERO_GREEN_BULLET_IMAGE;
    public static BufferedImage GOLD_BULLET_IMAGE;
    public static BufferedImage MOB_ENEMY_IMAGE;
    public static BufferedImage ELITE_ENEMY_IMAGE;
    public static BufferedImage ELITE_PLUS_ENEMY_IMAGE;
    public static BufferedImage BOSS_ENEMY_IMAGE;

    public static BufferedImage HEALING_ITEM_IMAGE;
    public static BufferedImage FIREPOWERUP_ITEM_IMAGE;
    public static BufferedImage BOMB_ITEM_IMAGE;
    public static BufferedImage FIREPOWERUPPLUS_ITEM_IMAGE;
    public static BufferedImage DARTS_ITEM_IMAGE;

    public static BufferedImage SMALL_LOGO_IMAGE;
    public static BufferedImage SMALL_LOGO_RED_IMAGE;
    public static BufferedImage LOGO_IMAGE;
    public static BufferedImage GAME_BG_IMAGE;

    public static ImageIcon BACKGROUND_GIF;
    static {
        try {

            BACKGROUND_IMAGE  = ImageIO.read(new FileInputStream("src/images/bg.jpg"));
            BACKGROUND_IMAGE2 = ImageIO.read(new FileInputStream("src/images/bg2.jpg"));
            BACKGROUND_IMAGE3 = ImageIO.read(new FileInputStream("src/images/bg3.jpg"));
            BACKGROUND_IMAGE4 = ImageIO.read(new FileInputStream("src/images/bg4.jpg"));
            BACKGROUND_IMAGE5 = ImageIO.read(new FileInputStream("src/images/bg5.jpg"));

            HERO_IMAGE = ImageIO.read(new FileInputStream("src/images/hero.png"));
            MOB_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/mob.png"));
            HERO_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/bullet_hero.png"));
            ENEMY_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/bullet_enemy.png"));
            HERO_DARTS_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/darts_bullet.png"));
            HERO_GREEN_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/bullet_hero_green.png"));
            GOLD_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/gold_bullet.png"));
            ELITE_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/elite.png"));
            ELITE_PLUS_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/elitePlus.png"));
            BOSS_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/boss.png"));

            HEALING_ITEM_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_blood.png"));
            FIREPOWERUP_ITEM_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_bullet.png"));
            BOMB_ITEM_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_bomb.png"));
            FIREPOWERUPPLUS_ITEM_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_bulletPlus.png"));
            DARTS_ITEM_IMAGE = ImageIO.read(new FileInputStream("src/images/darts_item.png"));

            SMALL_LOGO_IMAGE = ImageIO.read(new FileInputStream("src/images/small_logo_1.png"));
            SMALL_LOGO_RED_IMAGE = ImageIO.read(new FileInputStream("src/images/small_logo_red.png"));
            LOGO_IMAGE = ImageIO.read(new FileInputStream("src/images/big_logo.png"));
            GAME_BG_IMAGE = ImageIO.read(new FileInputStream("src/images/gamebg.png"));
            String gifPath = "src/images/game-video.gif";
            BACKGROUND_GIF = new ImageIcon(gifPath);

            CLASSNAME_IMAGE_MAP.put(HeroAircraft.class.getName(), HERO_IMAGE);
            CLASSNAME_IMAGE_MAP.put(MobEnemy.class.getName(), MOB_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(HeroBullet.class.getName(), HERO_BULLET_IMAGE);
            CLASSNAME_IMAGE_MAP.put(EnemyBullet.class.getName(), ENEMY_BULLET_IMAGE);
            CLASSNAME_IMAGE_MAP.put(HeroDartsBullet.class.getName(), HERO_DARTS_BULLET_IMAGE);
            CLASSNAME_IMAGE_MAP.put(HeroGreenBullet.class.getName(), HERO_GREEN_BULLET_IMAGE);
            CLASSNAME_IMAGE_MAP.put(GoldBullet.class.getName(), GOLD_BULLET_IMAGE);
            CLASSNAME_IMAGE_MAP.put(EliteEnemy.class.getName(), ELITE_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(ElitePlusEnemy.class.getName(), ELITE_PLUS_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(BossEnemy.class.getName(), BOSS_ENEMY_IMAGE);

            CLASSNAME_IMAGE_MAP.put(HealingItem.class.getName(), HEALING_ITEM_IMAGE);
            CLASSNAME_IMAGE_MAP.put(FirePowerUpItem.class.getName(), FIREPOWERUP_ITEM_IMAGE);
            CLASSNAME_IMAGE_MAP.put(BombItem.class.getName(), BOMB_ITEM_IMAGE);
            CLASSNAME_IMAGE_MAP.put(FirePowerUpPlusItem.class.getName(), FIREPOWERUPPLUS_ITEM_IMAGE);
            CLASSNAME_IMAGE_MAP.put(DartsItem.class.getName(), DARTS_ITEM_IMAGE);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static BufferedImage get(String className) {
        return CLASSNAME_IMAGE_MAP.get(className);
    }

    public static BufferedImage get(Object obj) {
        if (obj == null) {
            return null;
        }
        return get(obj.getClass().getName());
    }

}
