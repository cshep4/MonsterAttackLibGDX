package com.cshep4.monsterattack.game.pickup;

import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.character.Player;

import static com.cshep4.monsterattack.game.constants.Constants.SHIELD;
import static com.cshep4.monsterattack.game.constants.Constants.SHIELD_TIME;

public class Shield extends PickupItem {
    private Shield(Rectangle rectangle, String texture) {
        super(rectangle, texture);
    }

    public static Shield create(float x, float y, float width, float height) {
        Rectangle rectangle = new Rectangle().setPosition(x,y).setSize(width, height);
        return new Shield(rectangle, SHIELD);
    }

    @Override
    protected void reward(Player player) {
        player.setRemainingShieldTime(player.getRemainingShieldTime() + SHIELD_TIME);
    }
}
