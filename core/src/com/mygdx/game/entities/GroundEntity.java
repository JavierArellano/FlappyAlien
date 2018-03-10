package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.mygdx.game.Constants.PIXELS_IN_METER;

/**
 * Creado por franj en 10/03/2018.
 */

public class GroundEntity extends Actor {
    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;

    public GroundEntity(World world, Texture texture, float x, float y) {
        this.world = world;
        this.texture = texture;


        BodyDef def = new BodyDef();
        def.position.set(x + 0.5f, y+0.5f);

        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(0.5f,0.5f);
        fixture = body.createFixture(box,1);
        fixture.setUserData("floor");
        box.dispose();

        setSize(1*PIXELS_IN_METER,1*PIXELS_IN_METER);
        setPosition(x *PIXELS_IN_METER, (y - 1) *PIXELS_IN_METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
}
