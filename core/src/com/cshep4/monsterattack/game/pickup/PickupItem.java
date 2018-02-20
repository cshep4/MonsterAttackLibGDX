package com.cshep4.monsterattack.game.pickup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.character.Player;
import com.cshep4.monsterattack.game.core.GameObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.cshep4.monsterattack.game.constants.Constants.PICKUP_DURATION;
import static java.lang.System.currentTimeMillis;

@EqualsAndHashCode(callSuper=false)
@Data
public abstract class PickupItem extends GameObject {
    private long spawnTime = currentTimeMillis();

    protected PickupItem(Rectangle rectangle, Texture texture) {
        super(rectangle, texture, 1, 1);
    }

    public boolean isPickedUp(Player player) {
        if (getRectangle().overlaps(player.getRectangle())) {
            reward(player);
            Gdx.app.log("Pickup!", getClass().getSimpleName());
            return true;
        }
        return false;
    }

    protected abstract void reward(Player player);

    public boolean hasExpired() {
        return currentTimeMillis() - spawnTime > PICKUP_DURATION;
    }
}
