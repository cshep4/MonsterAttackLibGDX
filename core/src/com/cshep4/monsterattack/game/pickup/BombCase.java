package com.cshep4.monsterattack.game.pickup;

import com.badlogic.gdx.math.Rectangle;
import com.cshep4.monsterattack.game.character.Player;

import static com.cshep4.monsterattack.game.constants.Constants.BOMB;
import static com.cshep4.monsterattack.game.constants.Constants.BOMB_NUMBER;

public class BombCase extends PickupItem {
    private BombCase(Rectangle rectangle, String texture) {
        super(rectangle, texture);
    }

    public static BombCase create(float x, float y, float width, float height) {
        Rectangle rectangle = new Rectangle().setPosition(x,y).setSize(width, height);
        return new BombCase(rectangle, BOMB);
    }

    @Override
    protected void reward(Player player) {
        player.setNumberOfBombs(player.getNumberOfBombs() + BOMB_NUMBER);
    }
}