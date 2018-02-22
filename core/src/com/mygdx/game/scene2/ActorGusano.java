package com.mygdx.game.scene2;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Creado por franj en 19/02/2018.
 */

public class ActorGusano extends Actor {
    private TextureRegion gusano;

    public ActorGusano(TextureRegion gusano) {
        this.gusano = gusano;
        setSize(gusano.getRegionWidth(),gusano.getRegionHeight());
    }

    @Override
    public void act(float delta) {
        setX(getX()-250*delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(gusano,getX(),getY());
    }
}
