package com.cshep4.monsterattack.game.factory;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraFactory {
    private CameraFactory() {}

    public static OrthographicCamera create(boolean yDown, float viewportWidth, float viewportHeight) {
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(yDown, viewportWidth, viewportHeight);

        return camera;
    }
}
