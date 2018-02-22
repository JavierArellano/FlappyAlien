package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.mygdx.game.Constants.PIXELS_IN_METER;

/**
 * Creado por franj en 20/02/2018.
 */

public class GusanoEntity extends Actor {
    Animation move;
    private World world;
    private Body body;
    private Fixture fixture;
    private boolean flip;
    float time = 0;

    public GusanoEntity(World world, Texture texture, float x, float y, boolean flip) {
        this.world = world;
        this.flip = flip;
        TextureRegion gusanoLava = new TextureRegion(texture, 372, 40, 53,147);
        TextureRegion gusanoLavaAni = new TextureRegion(texture, 477, 0, 52,147);

        BodyDef def = new BodyDef();
        if (flip) {
            def.position.set(x, y + 0.5f);
        }else{
            def.position.set(x,y);
        }

        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(0.53f,1.47f);
        fixture = body.createFixture(box,1);
        fixture.setUserData("gusano");
        box.dispose();

        move = new Animation(0.1f, gusanoLava, gusanoLavaAni);
        move.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        setSize(PIXELS_IN_METER*1.06f,PIXELS_IN_METER*2.94f);
    }

    @Override
    public void act(float delta) {
        time = time + delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame;
        frame = (TextureRegion) move.getKeyFrame(time);
        if (flip) {
            setPosition((body.getPosition().x-0.50f)*PIXELS_IN_METER,
                    (body.getPosition().y-1.98f)*PIXELS_IN_METER);
            batch.draw(frame, getX(), getY()+getHeight(), getWidth(), -getHeight());
        }else{
            setPosition((body.getPosition().x-0.50f)*PIXELS_IN_METER,
                    (body.getPosition().y-1.47f)*PIXELS_IN_METER);
            batch.draw(frame, getX(), getY(), getWidth(), getHeight());
        }
    }

    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
}
