package com.cshep4.monsterattack.game.pickup;

import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.character.Player;

import static com.cshep4.monsterattack.game.constants.Constants.LIFE;

public class Life extends PickupItem {
    private Life(Rectangle rectangle, String texture) {
        super(rectangle, texture);
    }

    public static Life create(float x, float y, float width, float height) {
        Rectangle rectangle = new Rectangle().setPosition(x,y).setSize(width, height);
        return new Life(rectangle, LIFE);
    }

    @Override
    protected void reward(Player player) {
        player.setHealth(player.getHealth()+1);
    }
}
