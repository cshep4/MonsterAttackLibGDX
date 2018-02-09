package com.cshep4.monsterattack.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.cshep4.monsterattack.game.character.Character;
import com.cshep4.monsterattack.game.character.Player;
import com.cshep4.monsterattack.game.factory.TextureFactory;

import static com.cshep4.monsterattack.game.constants.Constants.CHARACTER_MOVE_LEFT;
import static com.cshep4.monsterattack.game.constants.Constants.CHARACTER_MOVE_RIGHT;
import static com.cshep4.monsterattack.game.constants.Constants.PLAYER_SPEED;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Utils {
    private Utils() {}

    public static void moveCharacterTowardsPoint(Character character, float destinationX, float destinationY) {
        float dirX = destinationX - character.getX();
        float dirY = destinationY - character.getY();

        //normalise direction
        double hypotenuse = sqrt(dirX*dirX + dirY*dirY);
        dirX /= hypotenuse;
        dirY /= hypotenuse;

        character.setX(character.getX() + (dirX*(character.getXVel()* Gdx.graphics.getDeltaTime())));
        character.setY(character.getY() + (dirY*(character.getYVel()*Gdx.graphics.getDeltaTime())));
    }

    public static void movePlayerTowardsPoint(Player player, float destinationX, float destinationY) {
        if (isPlayerCloseToDestination(player, destinationX, destinationY)) {
            return;
        }

        float destXBottomLeftCorner = destinationX - (player.getWidth()/2);
        float destYBottomLeftCorner = destinationY - (player.getHeight()/2);
        float dirX = destXBottomLeftCorner - player.getX();
        float dirY = destYBottomLeftCorner - player.getY();

        //normalise direction
        double hypotenuse = sqrt(dirX*dirX + dirY*dirY);
        dirX /= hypotenuse;
        dirY /= hypotenuse;

        float xVelocity = dirX*(PLAYER_SPEED* Gdx.graphics.getDeltaTime());
        float yVelocity = dirY*(PLAYER_SPEED* Gdx.graphics.getDeltaTime());

        player.setX(player.getX() + xVelocity);
        player.setY(player.getY() + yVelocity);

        if (xVelocity < 0) {
            player.changeAnimation(TextureFactory.create(CHARACTER_MOVE_LEFT),6, 1);
        } else {
            player.changeAnimation(TextureFactory.create(CHARACTER_MOVE_RIGHT),6, 1);
        }
    }

    private static boolean isPlayerCloseToDestination(Player player, float destinationX, float destinationY) {
        return abs(player.getMidX() - destinationX) < player.getWidth()/4 &&
                abs(player.getMidY() - destinationY) < player.getHeight()/4;
    }
}
