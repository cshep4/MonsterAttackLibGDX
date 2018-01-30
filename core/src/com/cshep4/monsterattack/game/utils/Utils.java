package com.cshep4.monsterattack.game.utils;

import com.badlogic.gdx.Gdx;
import com.cshep4.monsterattack.game.character.Character;
import com.cshep4.monsterattack.game.character.Player;
import com.cshep4.monsterattack.game.factory.TextureFactory;

import static com.cshep4.monsterattack.game.constants.Constants.CHARACTER_MOVE_LEFT;
import static com.cshep4.monsterattack.game.constants.Constants.CHARACTER_MOVE_RIGHT;
import static com.cshep4.monsterattack.game.constants.Constants.PLAYER_SPEED;
import static java.lang.Math.sqrt;

public class Utils {
    private Utils() {}

    public static void moveCharacterTowardsPoint(Character character, float destinationX, float destinationY) {
        float dirX = destinationX - character.getRectangle().getX();
        float dirY = destinationY - character.getRectangle().getY();

        //normalise direction
        double hypotenuse = sqrt(dirX*dirX + dirY*dirY);
        dirX /= hypotenuse;
        dirY /= hypotenuse;


        character.getRectangle().setX(character.getRectangle().getX() + (dirX*(character.getXVel()* Gdx.graphics.getDeltaTime())));
        character.getRectangle().setY(character.getRectangle().getY() + (dirY*(character.getYVel()*Gdx.graphics.getDeltaTime())));
    }

    public static void movePlayerTowardsPoint(Player player, float destinationX, float destinationY) {
        float destXBottomLeftCorner = destinationX - (player.getRectangle().getWidth()/2);
        float destYBottomLeftCorner = destinationY - (player.getRectangle().getHeight()/2);
        float dirX = destXBottomLeftCorner - player.getRectangle().getX();
        float dirY = destYBottomLeftCorner - player.getRectangle().getY();

        //normalise direction
        double hypotenuse = sqrt(dirX*dirX + dirY*dirY);
        dirX /= hypotenuse;
        dirY /= hypotenuse;

        float xVelocity = dirX*(PLAYER_SPEED* Gdx.graphics.getDeltaTime());
        float yVelocity = dirY*(PLAYER_SPEED* Gdx.graphics.getDeltaTime());

        player.getRectangle().setX(player.getRectangle().getX() + xVelocity);
        player.getRectangle().setY(player.getRectangle().getY() + yVelocity);

        if (xVelocity < 0) {
            player.changeAnimation(TextureFactory.create(CHARACTER_MOVE_LEFT),6, 1);
        } else {
            player.changeAnimation(TextureFactory.create(CHARACTER_MOVE_RIGHT),6, 1);
        }
    }
}
