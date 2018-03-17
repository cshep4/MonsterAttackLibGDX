package com.cshep4.monsterattack.game.wrapper;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SoundTest {
    @Mock
    private Audio audio;

    @Mock
    private Files files;

    @Mock
    private com.badlogic.gdx.audio.Sound sound;

    @Before
    public void init() {
        Gdx.audio = audio;
        Gdx.files = files;

        when(audio.newSound(any())).thenReturn(sound);
    }

    @Test
    public void loadSounds() {
        Sound.loadSounds();

        verify(audio, times(8)).newSound(any());
    }

    @Test
    public void playBackgroundMusic() {
        Sound.loadSounds();
        Sound.playBackgroundMusic();

        verify(sound).play(0.5f);
        verify(sound).setLooping(any(Long.class), any(Boolean.class));
    }

    @Test
    public void stopBackgroundMusic() {
        Sound.loadSounds();
        Sound.stopBackgroundMusic();

        verify(sound).stop(any(Long.class));
    }

    @Test
    public void playEnemyDie() {
        Sound.loadSounds();
        Sound.playEnemyDie();

        verify(sound).play(1f);
    }

    @Test
    public void playEnemyHit() {
        Sound.loadSounds();
        Sound.playEnemyHit();

        verify(sound).play(1f);
    }

    @Test
    public void playExplode() {
        Sound.loadSounds();
        Sound.playExplode();

        verify(sound).play(1f);
    }

    @Test
    public void playGameOver() {
        Sound.loadSounds();
        Sound.playGameOver();

        verify(sound).play(1f);
    }

    @Test
    public void playMutateBomb() {
        Sound.loadSounds();
        Sound.playMutateBomb();

        verify(sound).play(1f);
    }

    @Test
    public void playMutateStandard() {
        Sound.loadSounds();
        Sound.playMutateStandard();

        verify(sound).play(1f);
    }

    @Test
    public void playSelect() {
        Sound.loadSounds();
        Sound.playSelect();

        verify(sound).play(1f);
    }

    @Test
    public void dispose() {
        Sound.loadSounds();
        Sound.dispose();

        verify(sound, times(8)).dispose();
    }
}