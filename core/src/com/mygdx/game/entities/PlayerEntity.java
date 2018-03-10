package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.mygdx.game.Constants;

import static com.mygdx.game.Constants.*;

/**
 * Creado por franj en 20/02/2018.
 */

public class PlayerEntity extends Actor {
    private TextureRegion textureD;
    private Animation move;
    private World world;
    private Body body;
    private Fixture fixture;
    private boolean alive = true;
    private boolean cambio = true;
    private float time=0;

    public PlayerEntity(World world, Texture texture, Vector2 posicion) {
        this.world = world;
        /*
            <SubTexture height="256" width="128" y="1536" x="0" name="alienBeige_hit.png"/>

            <SubTexture height="256" width="128" y="768" x="0" name="alienBeige_swim1.png"/>

            <SubTexture height="256" width="128" y="512" x="0" name="alienBeige_swim2.png"/>
         */

        TextureRegion texture1 = new TextureRegion(texture, 0, 768, 128,256);
        TextureRegion texture2 = new TextureRegion(texture, 0, 512, 128,256);
        TextureRegion textureD = new TextureRegion(texture, 0, 1536, 128,256);


        this.textureD = textureD;

        BodyDef def = new BodyDef();
        def.position.set(posicion);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(0.64f,1.28f);
        fixture = body.createFixture(box,1);
        fixture.setUserData("player");
        box.dispose();

        move = new Animation(0.15f, texture1, texture2);
        move.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        setSize(PIXELS_IN_METER*1.28f,PIXELS_IN_METER*2.56f);
    }

    @Override
    public void act(float delta) {
        time = time + delta;
        if (isAlive()){
            float speedy = body.getLinearVelocity().y;
            body.setLinearVelocity(VELOCITY_X,speedy);
        }
    }
    public void saltar(){
        Vector2 position = body.getPosition();
        body.applyLinearImpulse(0,20, position.x,position.y, true);
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame;
        frame = (TextureRegion) move.getKeyFrame(time);

        setPosition((body.getPosition().x-0.64f)*PIXELS_IN_METER,
                    (body.getPosition().y-1.28f)*PIXELS_IN_METER);
        if (isAlive()) {
            batch.draw(frame, getX(), getY(), getWidth(), getHeight());
        }else{
            batch.draw(textureD, getX(), getY(), getWidth(), getHeight());
        }
    }

    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    public boolean isCambio() {
        return cambio;
    }

    public void setCambio(boolean cambio) {
        this.cambio = cambio;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
