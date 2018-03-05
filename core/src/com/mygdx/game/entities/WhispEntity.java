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
 * Creado por franj en 21/02/2018.
 */

public class WhispEntity extends Actor {
    Animation move;
    private World world;
    private Body body;
    private Fixture fixture;
    private boolean mover;
    float time = 0;

    public WhispEntity(World world, Texture texture, float x, float y, boolean mover) {
        this.world = world;
        this.mover = mover;
        TextureRegion fly = new TextureRegion(texture, 262, 114, 57,45);
        TextureRegion fly_fly = new TextureRegion(texture, 71, 333, 65,39);
        /*
            <SubTexture height="45" width="57" y="114" x="262" name="fly.png"/>

            <SubTexture height="39" width="65" y="333" x="71" name="fly_fly.png"/>
        */

        BodyDef def = new BodyDef();

        if (mover) {
            def.position.set(x,y+1f);
            def.type = BodyDef.BodyType.DynamicBody;
        }else{
            def.position.set(x,y);
        }
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(0.57f,0.45f);
        fixture = body.createFixture(box,1);
        fixture.setUserData("mosca");
        box.dispose();

        move = new Animation(0.15f, fly, fly_fly);
        move.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        setSize(PIXELS_IN_METER*1.14f,PIXELS_IN_METER*0.9f);
    }

    @Override
    public void act(float delta) {
        time = time + delta;
        if(mover) {
            if (body.getLinearVelocity().y < -8.482326f && body.getPosition().y < 5.4754916f) {
                saltar();
            }
            if(body.getPosition().y <3f){
                saltar();
            }
        }
    }
    private void saltar(){
        Vector2 position = body.getPosition();
        if (position.y<3f) {
            body.applyLinearImpulse(0, 19, position.x, position.y, true);
        }else {
            body.applyLinearImpulse(0, 18, position.x, position.y, true);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame;
        frame = (TextureRegion) move.getKeyFrame(time);
        setPosition((body.getPosition().x-0.57f)*PIXELS_IN_METER,
                (body.getPosition().y-0.45f)*PIXELS_IN_METER);

        batch.draw(frame, getX(), getY(), getWidth(), getHeight());
    }

    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
}
