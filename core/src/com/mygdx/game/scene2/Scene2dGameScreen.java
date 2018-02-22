package com.mygdx.game.scene2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.BaseScreen;
import com.mygdx.game.MainGame;

/**
 * Creado por franj en 19/02/2018.
 */

public class Scene2dGameScreen extends BaseScreen {

    private Stage stage;
    private ActorJugador jugador;
    private ActorGusano gusano;
    private Texture textureAlien, textureEnemies;
    private TextureRegion gusanoLava;

    public Scene2dGameScreen(MainGame game) {
        super(game);
        textureAlien = new Texture("alienB.png");
        textureEnemies = new Texture("enemies.png");
        gusanoLava = new TextureRegion(textureEnemies, 372, 40, 53,147);
                /*
                height="147" width="53" y="40" x="372" name="snakeLava
                height="147" width="52" y="0" x="477"  name="snakeLava_ani
                */
    }

    @Override
    public void show() {

        stage = new Stage();
        stage.setDebugAll(true);

        jugador = new ActorJugador(textureAlien);
        gusano = new ActorGusano(gusanoLava);
        stage.addActor(jugador);
        stage.addActor(gusano);

        jugador.setPosition(20,100);
        gusano.setPosition(500,100);
    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.6f,0,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();

        comprobarColisiones();

        stage.draw();
    }

    private void comprobarColisiones() {
        if (jugador.isAlive() && jugador.getX() + jugador.getWidth() > gusano.getX()){
            System.out.println("Colisión");
            jugador.setAlive(false);
        }
    }

    @Override
    public void dispose() {
        textureAlien.dispose();
    }
}
