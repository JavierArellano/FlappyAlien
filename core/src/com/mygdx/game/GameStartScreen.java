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
 * Creado por franj en 10/03/2018.
 */

public class GameStartScreen  extends BaseScreen {

    private Stage stage;
    private Skin skin;
    private Image gameStart;
    private TextButton start, credits;

    public GameStartScreen(final MainGame game) {
        super(game);

        stage = new Stage(new FitViewport(1120, 630));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        gameStart = new Image(game.getManager().get("flappy.png", Texture.class));
        start = new TextButton("Inicio", skin);
        credits = new TextButton("Creditos", skin);
        start.getLabel().setFontScale(1.5f);
        credits.getLabel().setFontScale(1.5f);

        credits.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.gameCreditScreen);
            }
        });

        start.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.gameScreen);
            }
        });

        gameStart.setPosition(560 - (gameStart.getWidth() / 2), 460 - gameStart.getHeight());
        start.setSize(150, 75);
        start.setPosition(460, 150);

        credits.setSize(150,75);
        credits.setPosition(460,50);

        stage.addActor(credits);
        stage.addActor(start);
        stage.addActor(gameStart);
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
        Gdx.gl.glClearColor(0.063f, 0.131f, 0.191f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }
}
