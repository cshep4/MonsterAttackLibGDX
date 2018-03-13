package com.cshep4.monsterattack.game.utils;

import com.cshep4.monsterattack.game.character.Bomber;
import com.cshep4.monsterattack.game.character.Standard;
import com.cshep4.monsterattack.game.factory.AnimationFactory;
import com.cshep4.monsterattack.game.wrapper.Animation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.cshep4.monsterattack.game.utils.EnemyUtils.setAbility;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AnimationFactory.class)
public class EnemyUtilsTest {
    @Mock
    private Animation animationWrapper;

    private Standard standard;
    private Bomber bomber;

    @Before
    public void init() {
        mockStatic(AnimationFactory.class);
        Mockito.when(AnimationFactory.createAnimation(any(Integer.class), any(Integer.class), any(String.class))).thenReturn(animationWrapper);
    }

    @Test
    public void setAbility_bomberLevel1() {
        bomber = Bomber.create(0, 0, 1);

        setAbility(bomber);

        assertThat(bomber.getHealth(), is(1));
        assertThat(bomber.getShieldHealth(), is(0));
        assertThat(bomber.isCanShoot(), is(false));
        assertThat(bomber.isCanDodge(), is(false));
        assertThat(bomber.isCanShield(), is(false));
        assertThat(bomber.isCanShootBombs(), is(false));
    }

    @Test
    public void setAbility_bomberLevel2() {
        bomber = Bomber.create(0, 0, 2);

        setAbility(bomber);

        assertThat(bomber.getHealth(), is(1));
        assertThat(bomber.getShieldHealth(), is(0));
        assertThat(bomber.isCanShoot(), is(true));
        assertThat(bomber.isCanDodge(), is(false));
        assertThat(bomber.isCanShield(), is(false));
        assertThat(bomber.isCanShootBombs(), is(false));
    }

    @Test
    public void setAbility_bomberLevel3() {
        bomber = Bomber.create(0, 0, 3);

        setAbility(bomber);

        assertThat(bomber.getHealth(), is(2));
        assertThat(bomber.getShieldHealth(), is(0));
        assertThat(bomber.isCanShoot(), is(true));
        assertThat(bomber.isCanDodge(), is(false));
        assertThat(bomber.isCanShield(), is(false));
        assertThat(bomber.isCanShootBombs(), is(false));
    }

    @Test
    public void setAbility_bomberLevel4() {
        bomber = Bomber.create(0, 0, 4);

        setAbility(bomber);

        assertThat(bomber.getHealth(), is(2));
        assertThat(bomber.getShieldHealth(), is(0));
        assertThat(bomber.isCanShoot(), is(true));
        assertThat(bomber.isCanDodge(), is(false));
        assertThat(bomber.isCanShield(), is(false));
        assertThat(bomber.isCanShootBombs(), is(true));
    }

    @Test
    public void setAbility_standardLevel1() {
        standard = Standard.create(0, 0, 1);

        setAbility(standard);

        assertThat(standard.getHealth(), is(1));
        assertThat(standard.getShieldHealth(), is(0));
        assertThat(standard.isCanShoot(), is(false));
        assertThat(standard.isCanDodge(), is(false));
        assertThat(standard.isCanShield(), is(false));
        assertThat(standard.isCanShootBombs(), is(false));
    }

    @Test
    public void setAbility_standardLevel2() {
        standard = Standard.create(0, 0, 2);

        setAbility(standard);

        assertThat(standard.getHealth(), is(1));
        assertThat(standard.getShieldHealth(), is(0));
        assertThat(standard.isCanShoot(), is(true));
        assertThat(standard.isCanDodge(), is(false));
        assertThat(standard.isCanShield(), is(false));
        assertThat(standard.isCanShootBombs(), is(false));
    }

    @Test
    public void setAbility_standardLevel3() {
        standard = Standard.create(0, 0, 3);

        setAbility(standard);

        assertThat(standard.getHealth(), is(2));
        assertThat(standard.getShieldHealth(), is(0));
        assertThat(standard.isCanShoot(), is(true));
        assertThat(standard.isCanDodge(), is(true));
        assertThat(standard.isCanShield(), is(false));
        assertThat(standard.isCanShootBombs(), is(false));
    }

    @Test
    public void setAbility_standardLevel4() {
        standard = Standard.create(0, 0, 4);

        setAbility(standard);

        assertThat(standard.getHealth(), is(1));
        assertThat(standard.getShieldHealth(), is(2));
        assertThat(standard.isCanShoot(), is(true));
        assertThat(standard.isCanDodge(), is(true));
        assertThat(standard.isCanShield(), is(true));
        assertThat(standard.isCanShootBombs(), is(false));
    }
}