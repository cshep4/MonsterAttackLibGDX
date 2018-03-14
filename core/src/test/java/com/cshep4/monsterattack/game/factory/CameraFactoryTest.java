package com.cshep4.monsterattack.game.factory;

import com.badlogic.gdx.graphics.OrthographicCamera;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CameraFactory.class)
public class CameraFactoryTest {
    @Mock
    private OrthographicCamera camera;

    @Test
    public void create_createsInstanceAndCallsSetToOrtho() throws Exception {
        boolean yDown = false;
        float viewportWidth = 10;
        float viewportHeight = 10;

        whenNew(OrthographicCamera.class).withAnyArguments().thenReturn(camera);

        OrthographicCamera orthographicCamera = CameraFactory.create(yDown, viewportWidth, viewportHeight);

        verify(camera).setToOrtho(yDown, viewportWidth, viewportHeight);
    }
}