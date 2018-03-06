package com.cshep4.monsterattack.game.pickup;

import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.character.Player;

import static com.cshep4.monsterattack.game.constants.Constants.BULLET_NUMBER;
import static com.cshep4.monsterattack.game.constants.Constants.PICKUP_BULLET;

public class BulletCase extends PickupItem {
    private BulletCase(Rectangle rectangle, String texture) {
        super(rectangle, texture);
    }

    public static BulletCase create(float x, float y, float width, float height) {
        Rectangle rectangle = new Rectangle().setPosition(x,y).setSize(width, height);
        return new BulletCase(rectangle, PICKUP_BULLET);
    }

    @Override
    protected void reward(Player player) {
        player.setNumberOfBullets(player.getNumberOfBullets() + BULLET_NUMBER);
    }
}
