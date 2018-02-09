package com.cshep4.monsterattack.game.utils;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.cshep4.monsterattack.game.character.Character;
import com.cshep4.monsterattack.game.character.Player;
import com.cshep4.monsterattack.game.factory.TextureFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.cshep4.monsterattack.game.constants.Constants.PLAYER_SPEED;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UtilsTest {
    private static final int START_X = 100;
    private static final int START_Y = 100;
    private static final int DESTINATION_X = 400;
    private static final int DESTINATION_Y = 400;
    private static final int START_DIFFERENCE_X = 300;
    private static final int START_DIFFERENCE_Y = 300;

    @Mock
    private Texture texture;

    @Mock
    private Graphics graphics;

    @Mock
    private Files files;

    @Mock
    private Application app;

    @Before
    public void init() {
        Gdx.graphics = graphics;
        Gdx.files = files;
        Gdx.app = app;

        doNothing().when(app).log(any(String.class), any(String.class));
        when(files.internal(any(String.class))).thenReturn(new FileHandle(""));
        when(graphics.getDeltaTime()).thenReturn(1f);
        when(texture.getWidth()).thenReturn(100);
        when(texture.getHeight()).thenReturn(100);
        TextureFactory.setTexture(texture);
    }

    @Test
    public void moveCharacterTowardsPoint() throws Exception {
        Rectangle rectangle = new Rectangle();
        rectangle.setX(START_X);
        rectangle.setY(START_Y);
        Character character = Player.create(1,1);
        character.setXVel(PLAYER_SPEED);
        character.setYVel(PLAYER_SPEED);

        Utils.moveCharacterTowardsPoint(character, DESTINATION_X, DESTINATION_Y);

        final float endDifferenceX = DESTINATION_X - character.getX();
        final float endDifferenceY = DESTINATION_Y - character.getY();

        assertTrue(START_DIFFERENCE_X > endDifferenceX);
        assertTrue(START_DIFFERENCE_Y > endDifferenceY);
        //should equal same as both start X and Y and end X and Y are same
        assertEquals(character.getX(), character.getY());
    }

    @Test
    public void movePlayerTowardsPoint() throws Exception {
        Rectangle rectangle = new Rectangle();
        rectangle.setX(START_X);
        rectangle.setY(START_Y);
        Player player = Player.create(1,1);
        player.setXVel(PLAYER_SPEED);
        player.setYVel(PLAYER_SPEED);

        Utils.movePlayerTowardsPoint(player, DESTINATION_X, DESTINATION_Y);

        final float endDifferenceX = DESTINATION_X - player.getX();
        final float endDifferenceY = DESTINATION_Y - player.getY();

        assertTrue(START_DIFFERENCE_X > endDifferenceX);
        assertTrue(START_DIFFERENCE_Y > endDifferenceY);
    }

}