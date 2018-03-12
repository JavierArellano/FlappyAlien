package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Creado por franj en 12/03/2018.
 */

public class GameCreditScreen extends BaseScreen {

    private Stage stage;
    private Skin skin;
    private Image gameOver;
    private TextButton volver;
    private Label creditos;

    public GameCreditScreen(final MainGame game) {
        super(game);

        stage = new Stage(new FitViewport(1120,630));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        gameOver = new Image(game.getManager().get("flappy.png", Texture.class));
        volver = new TextButton("Volver", skin);
        creditos = new Label("Juego Desarrollado por:\nFrancisco Javier Arellano Carrascosa\n2ºDAM", skin);

        volver.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.gameStartScreen);
            }
        });

        gameOver.setPosition(560-(gameOver.getWidth()/2), 460-gameOver.getHeight());
        volver.setSize(150,75);
        volver.setPosition(460,50);
        creditos.setPosition(460, 170);

        stage.addActor(creditos);
        stage.addActor(volver);
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
