package com.cshep4.monsterattack.game.utils;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.bullet.Bomb;
import com.cshep4.monsterattack.game.bullet.Bullet;
import com.cshep4.monsterattack.game.character.Player;
import com.cshep4.monsterattack.game.core.GameObject;
import com.cshep4.monsterattack.game.factory.AnimationFactory;
import com.cshep4.monsterattack.game.wrapper.Animation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.cshep4.monsterattack.game.core.CharacterType.PLAYER;
import static com.cshep4.monsterattack.game.utils.Utils.HAS_BULLET_SHOT_BOMB;
import static com.cshep4.monsterattack.game.utils.Utils.hasCollided;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AnimationFactory.class)
public class UtilsTest {
    private static final int MIN = 1;
    private static final int MAX = 100;

    @Mock
    private Animation animationWrapper;

    @Mock
    private BitmapFont font;

    @Before
    public void init() {
        mockStatic(AnimationFactory.class);
        Mockito.when(AnimationFactory.createAnimation(any(Integer.class), any(Integer.class), any(String.class))).thenReturn(animationWrapper);
    }

    @Test
    public void getRandomNumber_randomNumberReturnedWithinRange() {
        int result = Utils.getRandomNumber(MIN, MAX);

        assertThat(result, greaterThan(MIN - 1));
        assertThat(result, lessThan(MAX + 1));
    }

    @Test
    public void hasCollided_returnsTrueWhenOverlaps() {
        GameObject obj1 = Player.create(10, 10);
        GameObject obj2 = Player.create(10, 10);

        boolean result = hasCollided(obj1, obj2);

        assertThat(result, is(true));
    }

    @Test
    public void hasCollided_returnsFalseWhenDoesNotOverlap() {
        GameObject obj1 = Player.create(10, 10);
        GameObject obj2 = Player.create(10000, 10000);

        boolean result = hasCollided(obj1, obj2);

        assertThat(result, is(false));
    }

    @Test
    public void hasCollided1_returnsTrueWhenOverlaps() {
        Rectangle obj1 = new Rectangle(10, 10, 10, 10);
        GameObject obj2 = Player.create(15, 15);

        boolean result = hasCollided(obj1, obj2);

        assertThat(result, is(true));
    }

    @Test
    public void hasCollided1_returnsFalseWhenDoesNotOverlap() {
        Rectangle obj1 = new Rectangle(10, 10, 10, 10);
        GameObject obj2 = Player.create(150, 150);

        boolean result = hasCollided(obj1, obj2);

        assertThat(result, is(false));
    }

    @Test
    public void hasBulletShotBomb_returnsTrueWhenBombCollides() {
        Bullet bullet = Bullet.create(PLAYER, 10, 10, 10, 10);
        Bomb bomb = Bomb.create(PLAYER, 10, 10, 10, 10);

        boolean result = HAS_BULLET_SHOT_BOMB.test(bullet, bomb);

        assertThat(result, is(true));
    }

    @Test
    public void hasBulletShotBomb_returnsFalseWhenTwoBulletsCollides() {
        Bullet bullet1 = Bullet.create(PLAYER, 10, 10, 10, 10);
        Bullet bullet2 = Bullet.create(PLAYER, 10, 10, 10, 10);

        boolean result = HAS_BULLET_SHOT_BOMB.test(bullet1, bullet2);

        assertThat(result, is(false));
    }

    @Test
    public void hasBulletShotBomb_returnsFalseWhenNoCollision() {
        Bullet bullet = Bullet.create(PLAYER, 10, 10, 10, 10);
        Bomb bomb = Bomb.create(PLAYER, 100, 100, 100, 100);

        boolean result = HAS_BULLET_SHOT_BOMB.test(bullet, bomb);

        assertThat(result, is(false));
    }
}