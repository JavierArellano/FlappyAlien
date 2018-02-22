package com.mygdx.game.scene2;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Creado por franj en 19/02/2018.
 */

public class ActorJugador extends Actor {

    private Texture jugador;
    private Boolean alive;

    public ActorJugador(Texture jugador) {
        this.jugador = jugador;
        this.alive = true;
        setSize(jugador.getWidth(),jugador.getHeight());
    }

    public Boolean isAlive() {
        return alive;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(jugador, getX(),getY());
    }
}
