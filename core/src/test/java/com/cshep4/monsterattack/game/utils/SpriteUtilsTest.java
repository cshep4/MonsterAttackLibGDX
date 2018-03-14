package com.cshep4.monsterattack.game.utils;

import com.cshep4.monsterattack.game.character.BomberProducer;
import com.cshep4.monsterattack.game.character.StandardProducer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.cshep4.monsterattack.game.constants.Constants.B1_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.constants.Constants.B2_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.constants.Constants.B3_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.constants.Constants.B4_SPRITE_MOVE;
import static com.cshep4.monsterattack.game.constants.Constants.BP_IDLE_COLS;
import static com.cshep4.monsterattack.game.constants.Constants.BP_IDLE_ROWS;
import static com.cshep4.monsterattack.game.constants.Constants.BP_PRODUCING_COLS;
import static com.cshep4.monsterattack.game.constants.Constants.BP_PRODUCING_ROWS;
import static com.cshep4.monsterattack.game.constants.Constants.BP_SPRITE_IDLE;
import static com.cshep4.monsterattack.game.constants.Constants.BP_SPRITE_PRODUCE;
import static com.cshep4.monsterattack.game.constants.Constants.CHARACTER_IDLE;
import static com.cshep4.monsterattack.game.constants.Constants.CHARACTER_IDLE_SHIELD;
import static com.cshep4.monsterattack.game.constants.Constants.CHARACTER_MOVE_LEFT;
import static com.cshep4.monsterattack.game.constants.Constants.CHARACTER_MOVE_LEFT_SHIELD;
import static com.cshep4.monsterattack.game.constants.Constants.CHARACTER_MOVE_RIGHT;
import static com.cshep4.monsterattack.game.constants.Constants.CHARACTER_MOVE_RIGHT_SHIELD;
import static com.cshep4.monsterattack.game.constants.Constants.PLAYER_SPEED;
import static com.cshep4.monsterattack.game.constants.Constants.S1_SPRITE_MOVE_LEFT;
import static com.cshep4.monsterattack.game.constants.Constants.S1_SPRITE_MOVE_RIGHT;
import static com.cshep4.monsterattack.game.constants.Constants.S2_SPRITE_MOVE_LEFT;
import static com.cshep4.monsterattack.game.constants.Constants.S2_SPRITE_MOVE_RIGHT;
import static com.cshep4.monsterattack.game.constants.Constants.S3_SPRITE_MOVE_LEFT;
import static com.cshep4.monsterattack.game.constants.Constants.S3_SPRITE_MOVE_RIGHT;
import static com.cshep4.monsterattack.game.constants.Constants.S4_SPRITE_MOVE_LEFT;
import static com.cshep4.monsterattack.game.constants.Constants.S4_SPRITE_MOVE_RIGHT;
import static com.cshep4.monsterattack.game.constants.Constants.SP_IDLE_COLS;
import static com.cshep4.monsterattack.game.constants.Constants.SP_IDLE_ROWS;
import static com.cshep4.monsterattack.game.constants.Constants.SP_PRODUCING_COLS;
import static com.cshep4.monsterattack.game.constants.Constants.SP_PRODUCING_ROWS;
import static com.cshep4.monsterattack.game.constants.Constants.SP_SPRITE_IDLE;
import static com.cshep4.monsterattack.game.constants.Constants.SP_SPRITE_PRODUCE;
import static com.cshep4.monsterattack.game.utils.SpriteUtils.getBomberSprite;
import static com.cshep4.monsterattack.game.utils.SpriteUtils.getPlayerIdleSprite;
import static com.cshep4.monsterattack.game.utils.SpriteUtils.getPlayerLeftSprite;
import static com.cshep4.monsterattack.game.utils.SpriteUtils.getPlayerRightSprite;
import static com.cshep4.monsterattack.game.utils.SpriteUtils.getPlayerRunningSprite;
import static com.cshep4.monsterattack.game.utils.SpriteUtils.getProducerIdleCols;
import static com.cshep4.monsterattack.game.utils.SpriteUtils.getProducerIdleRows;
import static com.cshep4.monsterattack.game.utils.SpriteUtils.getProducerIdleSprite;
import static com.cshep4.monsterattack.game.utils.SpriteUtils.getProducerProducingCols;
import static com.cshep4.monsterattack.game.utils.SpriteUtils.getProducerProducingRows;
import static com.cshep4.monsterattack.game.utils.SpriteUtils.getProducerProducingSprite;
import static com.cshep4.monsterattack.game.utils.SpriteUtils.getStandardSpriteLeft;
import static com.cshep4.monsterattack.game.utils.SpriteUtils.getStandardSpriteRight;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class SpriteUtilsTest {
    @Mock
    private StandardProducer standardProducer;

    @Mock
    private BomberProducer bomberProducer;

    @Test
    public void getStandardSpriteLeft_level1() {
        String sprite = getStandardSpriteLeft(1);

        assertThat(sprite, is(S1_SPRITE_MOVE_LEFT));
    }

    @Test
    public void getStandardSpriteLeft_level2() {
        String sprite = getStandardSpriteLeft(2);

        assertThat(sprite, is(S2_SPRITE_MOVE_LEFT));
    }

    @Test
    public void getStandardSpriteLeft_level3() {
        String sprite = getStandardSpriteLeft(3);

        assertThat(sprite, is(S3_SPRITE_MOVE_LEFT));
    }

    @Test
    public void getStandardSpriteLeft_level4() {
        String sprite = getStandardSpriteLeft(4);

        assertThat(sprite, is(S4_SPRITE_MOVE_LEFT));
    }

    @Test
    public void getStandardSpriteRight_level1() {
        String sprite = getStandardSpriteRight(1);

        assertThat(sprite, is(S1_SPRITE_MOVE_RIGHT));
    }

    @Test
    public void getStandardSpriteRight_level2() {
        String sprite = getStandardSpriteRight(2);

        assertThat(sprite, is(S2_SPRITE_MOVE_RIGHT));
    }

    @Test
    public void getStandardSpriteRight_level3() {
        String sprite = getStandardSpriteRight(3);

        assertThat(sprite, is(S3_SPRITE_MOVE_RIGHT));
    }

    @Test
    public void getStandardSpriteRight_level4() {
        String sprite = getStandardSpriteRight(4);

        assertThat(sprite, is(S4_SPRITE_MOVE_RIGHT));
    }

    @Test
    public void getBomberSprite_level1() {
        String sprite = getBomberSprite(1);

        assertThat(sprite, is(B1_SPRITE_MOVE));
    }

    @Test
    public void getBomberSprite_level2() {
        String sprite = getBomberSprite(2);

        assertThat(sprite, is(B2_SPRITE_MOVE));
    }

    @Test
    public void getBomberSprite_level3() {
        String sprite = getBomberSprite(3);

        assertThat(sprite, is(B3_SPRITE_MOVE));
    }

    @Test
    public void getBomberSprite_level4() {
        String sprite = getBomberSprite(4);

        assertThat(sprite, is(B4_SPRITE_MOVE));
    }

    @Test
    public void getProducerIdleSprite_bomber() {
        String sprite = getProducerIdleSprite(bomberProducer);

        assertThat(sprite, is(BP_SPRITE_IDLE));
    }

    @Test
    public void getProducerIdleSprite_standard() {
        String sprite = getProducerIdleSprite(standardProducer);

        assertThat(sprite, is(SP_SPRITE_IDLE));
    }

    @Test
    public void getProducerProducingSprite_bomber() {
        String sprite = getProducerProducingSprite(bomberProducer);

        assertThat(sprite, is(BP_SPRITE_PRODUCE));
    }

    @Test
    public void getProducerProducingSprite_standard() {
        String sprite = getProducerProducingSprite(standardProducer);

        assertThat(sprite, is(SP_SPRITE_PRODUCE));
    }

    @Test
    public void getProducerIdleCols_bomber() {
        int result = getProducerIdleCols(bomberProducer);

        assertThat(result, is(BP_IDLE_COLS));
    }

    @Test
    public void getProducerIdleCols_standard() {
        int result = getProducerIdleCols(standardProducer);

        assertThat(result, is(SP_IDLE_COLS));
    }

    @Test
    public void getProducerIdleRows_bomber() {
        int result = getProducerIdleRows(bomberProducer);

        assertThat(result, is(BP_IDLE_ROWS));
    }

    @Test
    public void getProducerIdleRows_standard() {
        int result = getProducerIdleRows(standardProducer);

        assertThat(result, is(SP_IDLE_ROWS));
    }

    @Test
    public void getProducerProducingCols_bomber() {
        int result = getProducerProducingCols(bomberProducer);

        assertThat(result, is(BP_PRODUCING_COLS));
    }

    @Test
    public void getProducerProducingCols_standard() {
        int result = getProducerProducingCols(standardProducer);

        assertThat(result, is(SP_PRODUCING_COLS));
    }

    @Test
    public void getProducerProducingRows_bomber() {
        int result = getProducerProducingRows(bomberProducer);

        assertThat(result, is(BP_PRODUCING_ROWS));
    }

    @Test
    public void getProducerProducingRows_standard() {
        int result = getProducerProducingRows(standardProducer);

        assertThat(result, is(SP_PRODUCING_ROWS));
    }

    @Test
    public void getPlayerIdleSprite_noShield() {
        String sprite = getPlayerIdleSprite(0);

        assertThat(sprite, is(CHARACTER_IDLE));
    }

    @Test
    public void getPlayerIdleSprite_shield() {
        String sprite = getPlayerIdleSprite(1);

        assertThat(sprite, is(CHARACTER_IDLE_SHIELD));
    }

    @Test
    public void getPlayerLeftSprite_noShield() {
        String sprite = getPlayerLeftSprite(0);

        assertThat(sprite, is(CHARACTER_MOVE_LEFT));
    }

    @Test
    public void getPlayerLeftSprite_shield() {
        String sprite = getPlayerLeftSprite(1);

        assertThat(sprite, is(CHARACTER_MOVE_LEFT_SHIELD));
    }

    @Test
    public void getPlayerRightSprite_noShield() {
        String sprite = getPlayerRightSprite(0);

        assertThat(sprite, is(CHARACTER_MOVE_RIGHT));
    }

    @Test
    public void getPlayerRightSprite_shield() {
        String sprite = getPlayerRightSprite(1);

        assertThat(sprite, is(CHARACTER_MOVE_RIGHT_SHIELD));
    }

    @Test
    public void getPlayerRunningSprite_returnsLeftSprite() {
        String result = getPlayerRunningSprite(-PLAYER_SPEED, 0);

        assertThat(result, is(CHARACTER_MOVE_LEFT));
    }

    @Test
    public void getPlayerRunningSprite_returnsRightSprite() {
        String result = getPlayerRunningSprite(PLAYER_SPEED, 0);

        assertThat(result, is(CHARACTER_MOVE_RIGHT));
    }
}