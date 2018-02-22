package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import javax.xml.soap.Text;

public class MainGame extends Game {

    /*
            <SubTexture height="147" width="53" y="40" x="372" name="snakeLava.png"/>

            <SubTexture height="147" width="52" y="0" x="477" name="snakeLava_ani.png"/>

            <SubTexture height="147" width="53" y="334" x="424" name="snakeLava_dead.png"/>

            <SubTexture height="147" width="53" y="239" x="371" name="snakeLava_hit.png"/>

            <SubTexture height="147" width="53" y="187" x="424" name="snakeSlime.png"/>

            <SubTexture height="147" width="52" y="0" x="425" name="snakeSlime_ani.png"/>

            <SubTexture height="147" width="53" y="92" x="319" name="snakeSlime_dead.png"/>

            <SubTexture height="147" width="53" y="239" x="371" name="snakeSlime_hit.png"/>
     */
    private AssetManager manager;
    public AssetManager getManager(){
        return manager;
    }
    @Override
    public void create() {
        manager = new AssetManager();
        manager.load("floor.png", Texture.class);
        manager.load("roof.png", Texture.class);
        manager.load("players.png", Texture.class);
        manager.load("enemies.png", Texture.class);
        manager.finishLoading();

        setScreen(new GameScreen(this));
    }

}
