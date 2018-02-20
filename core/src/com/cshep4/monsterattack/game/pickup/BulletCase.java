package com.cshep4.monsterattack.game.pickup;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.character.Player;
import com.cshep4.monsterattack.game.factory.TextureFactory;

import static com.cshep4.monsterattack.game.constants.Constants.BULLET_NUMBER;
import static com.cshep4.monsterattack.game.constants.Constants.PICKUP_BULLET;

public class BulletCase extends PickupItem {
    private BulletCase(Rectangle rectangle, Texture texture) {
        super(rectangle, texture);
    }

    public static BulletCase create(float x, float y, float width, float height) {
        Rectangle rectangle = new Rectangle().setPosition(x,y).setSize(width, height);
        Texture texture = TextureFactory.create(PICKUP_BULLET);

        return new BulletCase(rectangle, texture);
    }

    @Override
    protected void reward(Player player) {
        player.setNumberOfBullets(player.getNumberOfBullets() + BULLET_NUMBER);
    }
}
