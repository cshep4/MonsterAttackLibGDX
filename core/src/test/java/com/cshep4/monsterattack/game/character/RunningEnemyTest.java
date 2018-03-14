package com.cshep4.monsterattack.game.character;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.cshep4.monsterattack.GameScreen;
import com.cshep4.monsterattack.game.bullet.Bomb;
import com.cshep4.monsterattack.game.bullet.Bullet;
import com.cshep4.monsterattack.game.factory.AnimationFactory;
import com.cshep4.monsterattack.game.wrapper.Animation;
import com.cshep4.monsterattack.game.wrapper.Sound;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.cshep4.monsterattack.GameScreen.getScreenXMax;
import static com.cshep4.monsterattack.GameScreen.getScreenYMax;
import static com.cshep4.monsterattack.game.constants.Constants.BOMB_SIZE_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.BULLET_HEIGHT_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.BULLET_SPEED;
import static com.cshep4.monsterattack.game.constants.Constants.BULLET_WIDTH_DIVIDER;
import static com.cshep4.monsterattack.game.constants.Constants.ENEMY_SPEED;
import static com.cshep4.monsterattack.game.constants.Constants.SHOOT_DELAY;
import static com.cshep4.monsterattack.game.core.CharacterType.PLAYER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AnimationFactory.class, Sound.class, GameScreen.class})
public class RunningEnemyTest {
    private static final float X_POS = 200;
    private static final float Y_POS = 200;
    private static final float STOPPED_VEL = 0;
    private static final int LEVEL_1 = 1;
    private static final int LEVEL_2 = 2;
    private static final int LEVEL_3 = 3;
    private static final int LEVEL_4 = 4;
    private static final float SCREEN_DIMS = 450;
    private static final int BULLET_SIZE = 10;

    @Mock
    private Animation animationWrapper;

    @Mock
    private Graphics graphics;

    @Mock
    private Application app;

    @Mock
    private Player player;

    @Before
    public void init() {
        mockStatic(Sound.class);
        mockStatic(AnimationFactory.class);
        when(AnimationFactory.createAnimation(any(Integer.class), any(Integer.class), any(String.class))).thenReturn(animationWrapper);
        mockStatic(GameScreen.class);
        when(GameScreen.getScreenXMax()).thenReturn(SCREEN_DIMS);
        when(GameScreen.getScreenYMax()).thenReturn(SCREEN_DIMS);

        Gdx.graphics = graphics;
        when(graphics.getDeltaTime()).thenReturn(1f);
        Gdx.app = app;
        doNothing().when(app).log(any(String.class),any(String.class));
    }

    @Test
    public void shoot_bombCreatedWhenEnemyHasAbility() {
        Bomber bomber = Bomber.create(X_POS, Y_POS, LEVEL_4);

        Bullet bullet = bomber.shoot();

        float expectedWidth = (bomber.getWidth() / BOMB_SIZE_DIVIDER) * 4;
        float expectedHeight = (bomber.getHeight() / BOMB_SIZE_DIVIDER) * 4;
        float expectedX = bomber.getX() +(bomber.getWidth()/2)- expectedWidth/2;
        float expectedY = bomber.getY() +(bomber.getHeight()/2)- expectedHeight/2;

        assertThat(bomber.isCanShootBombs(), is(true));
        assertThat(bullet, instanceOf(Bomb.class));
        assertThat(bullet.getWidth(), is(expectedWidth));
        assertThat(bullet.getHeight(), is(expectedHeight));
        assertThat(bullet.getX(), is(expectedX));
        assertThat(bullet.getY(), is(expectedY));
    }

    @Test
    public void shoot_bulletCreatedWhenEnemyCannotShootBombs() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_4);

        Bullet bullet = standard.shoot();

        float expectedWidth = standard.getWidth() / BULLET_WIDTH_DIVIDER;
        float expectedHeight = standard.getHeight() / BULLET_HEIGHT_DIVIDER;
        float expectedX = standard.getX()+(standard.getWidth() /2);
        float expectedY = standard.getY()+(standard.getHeight() /2);

        assertThat(standard.isCanShootBombs(), is(false));
        assertThat(bullet, instanceOf(Bullet.class));
        assertThat(bullet.getWidth(), is(expectedWidth));
        assertThat(bullet.getHeight(), is(expectedHeight));
        assertThat(bullet.getX(), is(expectedX));
        assertThat(bullet.getY(), is(expectedY));
    }

    @Test
    public void shield_enemyShields() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_4);

        standard.shield();

        assertThat(standard.isShielding(), is(true));
        assertThat(standard.getXVel(), is(STOPPED_VEL));
        assertThat(standard.getYVel(), is(STOPPED_VEL));
    }

    @Test
    public void dodge_enemyDoesNotRunOffScreenWhenDodging() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_4);
        standard.setXVel(ENEMY_SPEED).setX(getScreenXMax() + standard.getWidth() + 1);

        standard.dodge();

        assertThat(standard.getXVel(), is(-ENEMY_SPEED));
    }

    @Test
    public void dodge_enemyRetreatsDownwardsWhenBulletIsHigh() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_4);
        standard.setBulletY(standard.getMidY() + 10);

        standard.dodge();

        assertThat(standard.getXVel(), is(ENEMY_SPEED));
        assertThat(standard.getYVel(), is(-ENEMY_SPEED/2));
    }

    @Test
    public void dodge_enemyRetreatsUpwardsWhenBulletIsLow() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_4);
        standard.setBulletY(standard.getMidY() - 10);

        standard.dodge();

        assertThat(standard.getXVel(), is(ENEMY_SPEED));
        assertThat(standard.getYVel(), is(ENEMY_SPEED/2));
    }

    @Test
    public void dodge_enemyRetreatsUpwardsWhenBulletIsHighButEnemyAboutToRunOffBottomOfScreen() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_4);
        standard.setY(-10);
        standard.setBulletY(standard.getMidY() + 10);

        standard.dodge();

        assertThat(standard.getXVel(), is(ENEMY_SPEED));
        assertThat(standard.getYVel(), is(ENEMY_SPEED/2));
    }

    @Test
    public void dodge_enemyRetreatsDownwardsWhenBulletIsLowButEnemyAboutToRunOffTopOfScreen() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_4);
        standard.setY(getScreenYMax() + standard.getHeight() + 10);
        standard.setBulletY(standard.getMidY() - 10);

        standard.dodge();

        assertThat(standard.getXVel(), is(ENEMY_SPEED));
        assertThat(standard.getYVel(), is(-ENEMY_SPEED/2));
    }

    @Test
    public void decisionTree_enemyShieldsWhenBulletIsCloseAndHasAbility() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_4);

        final float xClose = X_POS - 10;
        final float yClose = standard.getMidY();

        Bullet bullet = Bullet.create(PLAYER, xClose, yClose, BULLET_SIZE, BULLET_SIZE);
        List<Bullet> playerBullets = Collections.singletonList(bullet);
        List<Bullet> enemyBullets = Collections.emptyList();

        standard.decisionTree(player, playerBullets, enemyBullets);

        assertThat(standard.isCanShield(), is(true));
        assertThat(standard.getShieldHealth(), greaterThan(0));
        assertThat(standard.getXVel(), is(STOPPED_VEL));
        assertThat(standard.getYVel(), is(STOPPED_VEL));
        assertThat(standard.isShielding(), is(true));
    }

    @Test
    public void decisionTree_enemyDoesNotShieldWhenBulletIsCloseAndDoesNotHaveAbility() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_1);

        final float xClose = X_POS - 10;
        final float yClose = standard.getMidY();

        Bullet bullet = Bullet.create(PLAYER, xClose, yClose, BULLET_SIZE, BULLET_SIZE);
        List<Bullet> playerBullets = Collections.singletonList(bullet);
        List<Bullet> enemyBullets = Collections.emptyList();

        standard.decisionTree(player, playerBullets, enemyBullets);

        assertThat(standard.isCanShield(), is(false));
        assertThat(standard.getXVel(), is(-ENEMY_SPEED));
        assertThat(standard.getYVel(), is(STOPPED_VEL));
        assertThat(standard.isShielding(), is(false));
    }

    @Test
    public void decisionTree_enemyDoesNotShieldWhenBulletIsCloseAndDoesNotHaveAnyShieldHealth() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_4);
        standard.setShieldHealth(0);

        final float xClose = X_POS - 10;
        final float yClose = standard.getMidY();

        Bullet bullet = Bullet.create(PLAYER, xClose, yClose, BULLET_SIZE, BULLET_SIZE);
        List<Bullet> playerBullets = Collections.singletonList(bullet);
        List<Bullet> enemyBullets = Collections.emptyList();

        standard.decisionTree(player, playerBullets, enemyBullets);

        assertThat(standard.isCanShield(), is(true));
        assertThat(standard.getShieldHealth(), is(0));
        assertThat(standard.isShielding(), is(false));
    }

    @Test
    public void decisionTree_enemyDodgesWhenInLineOfBulletAndHasAbility() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_3);

        final float xClose = X_POS - 10;
        final float yClose = standard.getMidY();

        Bullet bullet = Bullet.create(PLAYER, xClose, yClose, BULLET_SIZE, BULLET_SIZE);
        List<Bullet> playerBullets = Collections.singletonList(bullet);
        List<Bullet> enemyBullets = Collections.emptyList();

        standard.decisionTree(player, playerBullets, enemyBullets);

        assertThat(standard.isCanDodge(), is(true));
        assertThat(standard.getXVel(), is(ENEMY_SPEED));
        assertThat(standard.isDodging(), is(true));
    }

    @Test
    public void decisionTree_enemyDoesNotDodgeWhenInLineOfBulletAndDoesNotHaveAbility() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_2);

        final float xClose = X_POS - 10;
        final float yClose = standard.getMidY();

        Bullet bullet = Bullet.create(PLAYER, xClose, yClose, BULLET_SIZE, BULLET_SIZE);
        List<Bullet> playerBullets = Collections.singletonList(bullet);
        List<Bullet> enemyBullets = Collections.emptyList();

        standard.decisionTree(player, playerBullets, enemyBullets);

        assertThat(standard.isCanDodge(), is(false));
        assertThat(standard.getXVel(), is(-ENEMY_SPEED));
        assertThat(standard.isDodging(), is(false));
    }

    @Test
    public void decisionTree_enemyShootsWhenPlayerInLineOfSightIfDelayHasPassedAndMovesForward() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_2);
        when(player.getX()).thenReturn(standard.getX() - 10);
        when(player.getY()).thenReturn(standard.getMidY());

        List<Bullet> playerBullets = Collections.emptyList();
        List<Bullet> enemyBullets = new ArrayList<>();

        //set shoot time to be before delay
        standard.setShootTime(System.currentTimeMillis() - SHOOT_DELAY -1);

        float expectedWidth = standard.getWidth() / BULLET_WIDTH_DIVIDER;
        float expectedHeight = standard.getHeight() / BULLET_HEIGHT_DIVIDER;
        float expectedX = standard.getX()+(standard.getWidth() /2);
        float expectedY = standard.getY()+(standard.getHeight() /2);

        float expectedEnemyX = standard.getX() - ENEMY_SPEED;
        float expectedEnemyY = standard.getY();

        standard.decisionTree(player, playerBullets, enemyBullets);

        Bullet bullet = enemyBullets.get(0);

        assertThat(standard.isCanShoot(), is(true));
        assertThat(enemyBullets.isEmpty(), is(false));
        assertThat(bullet, instanceOf(Bullet.class));
        assertThat(bullet.getX(), is(expectedX));
        assertThat(bullet.getY(), is(expectedY));
        assertThat(bullet.getWidth(), is(expectedWidth));
        assertThat(bullet.getHeight(), is(expectedHeight));
        assertThat(bullet.getXVel(), is(-BULLET_SPEED));
        assertThat(standard.getX(), is(expectedEnemyX));
        assertThat(standard.getY(), is(expectedEnemyY));
    }

    @Test
    public void decisionTree_enemyDoesNotShootWhenPlayerInLineOfSightAndDelayHasNotPassedAndMovesForward() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_2);
        when(player.getX()).thenReturn(standard.getX() - 10);
        when(player.getY()).thenReturn(standard.getMidY());

        List<Bullet> playerBullets = Collections.emptyList();
        List<Bullet> enemyBullets = new ArrayList<>();

        //set shoot time to be within delay period
        standard.setShootTime(System.currentTimeMillis() + SHOOT_DELAY);

        float expectedX = standard.getX() - ENEMY_SPEED;
        float expectedY = standard.getY();

        standard.decisionTree(player, playerBullets, enemyBullets);

        assertThat(standard.isCanShoot(), is(true));
        assertThat(enemyBullets.isEmpty(), is(true));
        assertThat(standard.getX(), is(expectedX));
        assertThat(standard.getY(), is(expectedY));
    }

    @Test
    public void decisionTree_enemyDoesNotShootWhenPlayerInLineOfSightAndDoesNotHaveAbilitySoMovesForward() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_1);
        when(player.getX()).thenReturn(standard.getX() - 10);
        when(player.getY()).thenReturn(standard.getMidY());

        List<Bullet> playerBullets = Collections.emptyList();
        List<Bullet> enemyBullets = new ArrayList<>();

        float expectedX = standard.getX() - ENEMY_SPEED;
        float expectedY = standard.getY();

        standard.decisionTree(player, playerBullets, enemyBullets);

        assertThat(standard.isCanShoot(), is(false));
        assertThat(enemyBullets.isEmpty(), is(true));
        assertThat(standard.getX(), is(expectedX));
        assertThat(standard.getY(), is(expectedY));
    }

    @Test
    public void decisionTree_enemyStopsDodgingWhenNoLongerInLineWithBullet() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_3);
        standard.setDodging(true);

        float yNotInline = Y_POS + 1000;

        Bullet bullet = Bullet.create(PLAYER, X_POS, yNotInline, BULLET_SIZE, BULLET_SIZE);
        List<Bullet> playerBullets = Collections.singletonList(bullet);
        List<Bullet> enemyBullets = Collections.emptyList();

        standard.decisionTree(player, playerBullets, enemyBullets);

        assertThat(standard.isCanDodge(), is(true));
        assertThat(standard.isDodging(), is(false));
    }

    @Test
    public void decisionTree_enemyStopsShieldingWhenBulletIsNotClose() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_4);
        standard.setShielding(true);

        float xNotClose = X_POS + 1000;

        Bullet bullet = Bullet.create(PLAYER, xNotClose, Y_POS, BULLET_SIZE, BULLET_SIZE);
        List<Bullet> playerBullets = Collections.singletonList(bullet);
        List<Bullet> enemyBullets = Collections.emptyList();

        standard.decisionTree(player, playerBullets, enemyBullets);

        assertThat(standard.isCanShield(), is(true));
        assertThat(standard.isShielding(), is(false));
    }

    @Test
    public void isBulletClose_returnsTrueIfPlayerBulletsAreClose() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_4);

        final float xClose = X_POS - 10;
        final float yClose = standard.getMidY();

        Bullet bullet = Bullet.create(PLAYER, xClose, yClose, BULLET_SIZE, BULLET_SIZE);
        List<Bullet> playerBullets = Collections.singletonList(bullet);

        boolean result = standard.isBulletClose(playerBullets);

        assertThat(result, is(true));
    }

    @Test
    public void isBulletClose_returnsFalseIfThereAreNoPlayerBullets() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_4);

        boolean result = standard.isBulletClose(Collections.emptyList());

        assertThat(result, is(false));
    }

    @Test
    public void isBulletClose_returnsFalseIfNoPlayerBulletsAreClose() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_4);

        final float xNotClose = X_POS - 1000;
        final float yClose = standard.getMidY();

        Bullet bullet = Bullet.create(PLAYER, xNotClose, yClose, BULLET_SIZE, BULLET_SIZE);
        List<Bullet> playerBullets = Collections.singletonList(bullet);

        boolean result = standard.isBulletClose(playerBullets);

        assertThat(result, is(false));
    }

    @Test
    public void isBulletClose_returnsFalseIfPlayerBulletsAreCloseButNotInline() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_4);

        final float xClose = X_POS - 10;
        final float yNotInline = standard.getMidY() + 1000;

        Bullet bullet = Bullet.create(PLAYER, xClose, yNotInline, BULLET_SIZE, BULLET_SIZE);
        List<Bullet> playerBullets = Collections.singletonList(bullet);

        boolean result = standard.isBulletClose(playerBullets);

        assertThat(result, is(false));
    }

    @Test
    public void isEnemyInLineOfBullet_returnsTrueIfPlayerBulletsAreInline() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_4);

        final float xNotClose = X_POS - 1000;
        final float yClose = standard.getMidY();

        Bullet bullet = Bullet.create(PLAYER, xNotClose, yClose, BULLET_SIZE, BULLET_SIZE);
        List<Bullet> playerBullets = Collections.singletonList(bullet);

        boolean result = standard.isEnemyInLineOfBullet(playerBullets);

        assertThat(result, is(true));
    }

    @Test
    public void isEnemyInLineOfBullet_returnsFalseIfThereAreNoPlayerBullets() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_4);

        boolean result = standard.isEnemyInLineOfBullet(Collections.emptyList());

        assertThat(result, is(false));
    }

    @Test
    public void isEnemyInLineOfBullet_returnsFalseIfNoPlayerBulletsAreInline() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_4);

        final float xNotClose = X_POS - 1000;
        final float yNotInline = standard.getMidY() + 1000;

        Bullet bullet = Bullet.create(PLAYER, xNotClose, yNotInline, BULLET_SIZE, BULLET_SIZE);
        List<Bullet> playerBullets = Collections.singletonList(bullet);

        boolean result = standard.isEnemyInLineOfBullet(playerBullets);

        assertThat(result, is(false));
    }

    @Test
    public void isPlayerInLineOfSight_returnsTrueIfPlayerInlineWithEnemyBullets() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_4);
        when(player.getX()).thenReturn(standard.getX() - 10);
        when(player.getY()).thenReturn(standard.getMidY());

        boolean result = standard.isPlayerInLineOfSight(player);

        assertThat(result, is(true));
    }

    @Test
    public void isPlayerInLineOfSight_returnsFalseIfPlayerNotInlineWithEnemyBullets() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_4);
        when(player.getX()).thenReturn(standard.getX() - 10);
        when(player.getY()).thenReturn(standard.getMidY() + 1000);

        boolean result = standard.isPlayerInLineOfSight(player);

        assertThat(result, is(false));
    }

    @Test
    public void checkShootDelay_returnsTrueIfASecondHasPassed() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_4);
        standard.setShootTime(System.currentTimeMillis() - SHOOT_DELAY -1);

        boolean result = standard.checkShootDelay();

        assertThat(result, is(true));
    }

    @Test
    public void checkShootDelay_returnsFalseIfASecondHasNotPassed() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_4);
        standard.setShootTime(System.currentTimeMillis() + SHOOT_DELAY);

        boolean result = standard.checkShootDelay();

        assertThat(result, is(false));
    }

    @Test
    public void moveForward_enemyRunsForward() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_4);
        standard.setXVel(0).setYVel(0);

        standard.moveForward();

        assertThat(standard.getXVel(), is(-ENEMY_SPEED));
        assertThat(standard.getYVel(), is(STOPPED_VEL));
    }

    @Test
    public void moveForward_enemyRunsForwardAndComesBackOnScreenIfOffTheTop() {
        float yOffTopOfScreen = getScreenYMax() + 10000;
        Standard standard = Standard.create(X_POS, yOffTopOfScreen, LEVEL_4);
        standard.setXVel(0).setYVel(0);

        standard.moveForward();

        assertThat(standard.getXVel(), is(-ENEMY_SPEED));
        assertThat(standard.getYVel(), is(-ENEMY_SPEED/2));
    }

    @Test
    public void moveForward_enemyRunsForwardAndComesBackOnScreenIfOffTheBottom() {
        float yOffBottomOfScreen = -100;
        Standard standard = Standard.create(X_POS, yOffBottomOfScreen, LEVEL_4);
        standard.setXVel(0).setYVel(0);

        standard.moveForward();

        assertThat(standard.getXVel(), is(-ENEMY_SPEED));
        assertThat(standard.getYVel(), is(ENEMY_SPEED/2));
    }

    @Test
    public void checkPlayerHasBeenKilled_killSelfAndHurtPlayerIfOffTheLeftHandSideOfTheScreen() {
        float xOffLeftHandSideOfScreen = -100;
        Standard standard = Standard.create(xOffLeftHandSideOfScreen, Y_POS, LEVEL_1);
        Player player = Player.create(X_POS, Y_POS);

        int expectedPlayerHealth = player.getHealth() - 1;

        standard.checkPlayerHasBeenKilled(player);

        assertThat(standard.getHealth(), is(0));
        assertThat(player.getHealth(), is(expectedPlayerHealth));
    }

    @Test
    public void checkPlayerHasBeenKilled_doNothingIfNotOffTheLeftHandSideOfTheScreen() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_1);
        Player player = Player.create(100000, 1000000);

        int expectedEnemyHealth = standard.getHealth();
        int expectedPlayerHealth = player.getHealth();

        standard.checkPlayerHasBeenKilled(player);

        assertThat(standard.getHealth(), is(expectedEnemyHealth));
        assertThat(player.getHealth(), is(expectedPlayerHealth));
    }

    @Test
    public void update_callsDecisionTreeThenChecksPlayerDeath() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_1);

        //set up player for collision to test that enemy has moved forward then collided, checking player death
        float playerX = standard.getX() - ENEMY_SPEED;
        float playerY = standard.getY();

        Player player = Player.create(playerX, playerY);

        int expectedPlayerHealth = player.getHealth() - 1;

        standard.update(player, Collections.emptyList(), Collections.emptyList());

        assertThat(standard.getX(), is(playerX));
        assertThat(standard.getY(), is(playerY));
        assertThat(standard.getHealth(), is(0));
        assertThat(player.getHealth(), is(expectedPlayerHealth));
    }

    @Test
    public void getLogName_returnsCorrectLogName() {
        Standard standard = Standard.create(X_POS, Y_POS, LEVEL_1);

        String expectedLogName = "Standard1";
        String actualLogName = standard.getLogName();

        assertThat(actualLogName, is(expectedLogName));
    }
}