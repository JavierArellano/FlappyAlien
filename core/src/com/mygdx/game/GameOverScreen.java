package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Creado por franj en 06/03/2018.
 */

public class GameOverScreen extends BaseScreen {

    private Stage stage;
    private Skin skin;
    private Image gameOver;
    private TextButton retry;

    public GameOverScreen(final MainGame game) {
        super(game);

        stage = new Stage(new FitViewport(1120,630));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        gameOver = new Image(game.getManager().get("gameover.png", Texture.class));
        retry = new TextButton("Reintentar", skin);

        retry.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.iniciarGameScreen();
                game.setScreen(game.gameScreen);
            }
        });

        gameOver.setPosition(560-(gameOver.getWidth()/2), 460-gameOver.getHeight());
        retry.setSize(200,100);
        retry.setPosition(460,150);

        stage.addActor(retry);
        stage.addActor(gameOver);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.063f,0.131f,0.191f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }
}
