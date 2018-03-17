package com.cshep4.monsterattack.game.pickup;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.cshep4.monsterattack.game.character.Player;
import com.cshep4.monsterattack.game.factory.AnimationFactory;
import com.cshep4.monsterattack.game.wrapper.Animation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.cshep4.monsterattack.game.constants.Constants.BULLET_NUMBER;
import static com.cshep4.monsterattack.game.constants.Constants.PICKUP_SIZE_DIVIDER;
import static com.cshep4.monsterattack.game.utils.Utils.getScreenXMax;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AnimationFactory.class})
public class BulletCaseTest {
    private static final float X_POS = 123;
    private static final float Y_POS = 123;
    private static final float SIZE = getScreenXMax() / PICKUP_SIZE_DIVIDER;

    @Mock
    private Animation animationWrapper;

    @Mock
    private Application app;

    @Before
    public void init() {
        mockStatic(AnimationFactory.class);
        when(AnimationFactory.createAnimation(any(Integer.class), any(Integer.class), any(String.class))).thenReturn(animationWrapper);

        Gdx.app = app;
        doNothing().when(app).log(any(String.class),any(String.class));
    }

    @Test
    public void create() {
        BulletCase bulletCase = BulletCase.create(X_POS, Y_POS, SIZE, SIZE);

        assertThat(bulletCase, instanceOf(BulletCase.class));
        assertThat(bulletCase.getX(), is(X_POS));
        assertThat(bulletCase.getY(), is(Y_POS));
        assertThat(bulletCase.getWidth(), is(SIZE));
        assertThat(bulletCase.getHeight(), is(SIZE));
    }

    @Test
    public void reward_givesPlayerTenBullets() {
        Player player = Player.create(X_POS, Y_POS);
        BulletCase bulletCase = BulletCase.create(X_POS, Y_POS, SIZE, SIZE);

        int expectedNumberOfBullets = player.getNumberOfBullets() + BULLET_NUMBER;

        bulletCase.reward(player);

        assertThat(player.getNumberOfBullets(), is(expectedNumberOfBullets));
    }
}