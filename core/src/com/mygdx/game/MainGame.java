package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import javax.xml.soap.Text;

public class MainGame extends Game {
    public GameScreen gameScreen;
    public GameOverScreen gameOverScreen;
    public GameStartScreen gameStartScreen;
    private AssetManager manager;

    public AssetManager getManager(){
        return manager;
    }
    @Override
    public void create() {
        manager = new AssetManager();
        manager.load("floor.png", Texture.class);
        manager.load("roof.png", Texture.class);
        manager.load("ground.png", Texture.class);
        manager.load("players.png", Texture.class);
        manager.load("enemies.png", Texture.class);
        manager.load("gameover.png", Texture.class);
        manager.load("flappy.png", Texture.class);
        manager.load("audio/dolorsito.mp3", Sound.class);
        manager.load("audio/salto.mp3", Sound.class);
        manager.load("audio/music.wav", Music.class);
        manager.finishLoading();

        gameScreen = new GameScreen(this);
        gameOverScreen = new GameOverScreen(this);
        gameStartScreen = new GameStartScreen(this);

        setScreen(gameStartScreen);
    }

}
