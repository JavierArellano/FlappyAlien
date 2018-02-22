package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Creado por franj en 19/02/2018.
 */

public class Box2dScreen extends BaseScreen {

    private World world;
    private Box2DDebugRenderer renderer;
    private OrthographicCamera camera;
    private Body alienBody, sueloBody, techoBody, gusanoBody;
    private Fixture alienFixture, sueloFixture, techoFixture, gusanoFixture;
    private Boolean colision, alienVivo=true;

    public Box2dScreen(MainGame game) {
        super(game);
    }

    @Override
    public void show() {
        world = new World(new Vector2(0,-10),true);
        renderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(32,16);
        camera.translate(0,7);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA(), fixtureB = contact.getFixtureB();
                if ((fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("floor")) ||
                        (fixtureA.getUserData().equals("floor") && fixtureB.getUserData().equals("player"))){
                    alienVivo = false;
                }
                if ((fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("roof")) ||
                        (fixtureA.getUserData().equals("roof") && fixtureB.getUserData().equals("player"))){
                    alienVivo = false;
                }
                if ((fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("worm")) ||
                        (fixtureA.getUserData().equals("worm") && fixtureB.getUserData().equals("player"))){
                    alienVivo = false;
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

        alienBody = world.createBody(createBodyDef());
        sueloBody = world.createBody(createSBodyDef());
        techoBody = world.createBody(createTBodyDef());
        gusanoBody = world.createBody(createGBodyDef(15));


        PolygonShape alienShape = new PolygonShape();
        alienShape.setAsBox(0.66f,0.94f);
        alienFixture = alienBody.createFixture(alienShape,1);
        alienShape.dispose();

        PolygonShape gusanoShape = new PolygonShape();
        gusanoShape.setAsBox(0.53f,1.47f);
        gusanoFixture = gusanoBody.createFixture(gusanoShape,1);
        gusanoShape.dispose();

        PolygonShape sueloShape = new PolygonShape();
        sueloShape.setAsBox(500,1);
        sueloFixture = sueloBody.createFixture(sueloShape, 1);
        sueloShape.dispose();

        PolygonShape techoShape = new PolygonShape();
        techoShape.setAsBox(500,1);
        techoFixture = techoBody.createFixture(techoShape, 1);
        techoShape.dispose();

        alienFixture.setUserData("player");
        sueloFixture.setUserData("floor");
        techoFixture.setUserData("roof");
        gusanoFixture.setUserData("worm");
    }

    private BodyDef createGBodyDef(float x) {
        BodyDef def = new BodyDef();
        def.position.set(x,1.47f);
        return def;
    }

    private BodyDef createTBodyDef() {
        BodyDef def = new BodyDef();
        def.position.set(0,15);
        return def;
    }

    private BodyDef createSBodyDef() {
        BodyDef def = new BodyDef();
        def.position.set(0,-1);
        return def;
    }

    private BodyDef createBodyDef() {
        BodyDef def = new BodyDef();
        def.position.set(-4,7);
        def.type = BodyDef.BodyType.DynamicBody;
        return def;
    }


    @Override
    public void dispose() {
        alienBody.destroyFixture(alienFixture);
        sueloBody.destroyFixture(sueloFixture);
        techoBody.destroyFixture(techoFixture);
        gusanoBody.destroyFixture(gusanoFixture);
        world.destroyBody(alienBody);
        world.destroyBody(sueloBody);
        world.destroyBody(techoBody);
        world.destroyBody(gusanoBody);
        world.dispose();
        renderer.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.6f,0,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (alienVivo){
            if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.UP)){
                saltar();
            }
            float velocidadY = alienBody.getLinearVelocity().y;
            alienBody.setLinearVelocity(10,velocidadY);
        }

        world.step(delta, 6,2);
        camera.update();
        renderer.render(world, camera.combined);
    }

    private void saltar(){
        Vector2 position = alienBody.getPosition();
        alienBody.applyLinearImpulse(0,20, position.x,position.y, true);
    }
}
