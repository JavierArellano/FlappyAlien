package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.entities.FloorEntity;
import com.mygdx.game.entities.GusanoEntity;
import com.mygdx.game.entities.PlayerEntity;
import com.mygdx.game.entities.WhispEntity;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.Constants.PIXELS_IN_METER;
import static com.mygdx.game.Constants.VELOCITY_X;

/**
 * Creado por franj en 20/02/2018.
 */

public class GameScreen extends BaseScreen {

    private Stage stage;
    private World world;
    private PlayerEntity player;
    private List<FloorEntity> floorList = new ArrayList<FloorEntity>();
    private List<FloorEntity> roofList = new ArrayList<FloorEntity>();
    private List<GusanoEntity> gusanoList = new ArrayList<GusanoEntity>();
    private List<WhispEntity> whispList = new ArrayList<WhispEntity>();

    public GameScreen(MainGame game) {
        super(game);
        stage = new Stage(new FitViewport(1120,630));
        world = new World(new Vector2(0,-10), true);

        world.setContactListener(new ContactListener() {
            private boolean areCollided(Contact contact, Object userA, Object userB){
                return contact.getFixtureA().getUserData().equals(userA)&& contact.getFixtureB().getUserData().equals(userB) ||
                        contact.getFixtureA().getUserData().equals(userB) && contact.getFixtureB().getUserData().equals(userA);
            }
            @Override
            public void beginContact(Contact contact) {
                if (areCollided(contact, "player", "floor")){
                    player.setAlive(false);
                }
                if (areCollided(contact, "player", "gusano")){
                    player.setAlive(false);
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
    }

    @Override
    public void show() {
        Texture playerTexture = game.getManager().get("players.png");
        Texture enemyTexture = game.getManager().get("enemies.png");
        Texture floorTexture = game.getManager().get("floor.png");
        Texture roofTexture = game.getManager().get("roof.png");

        player = new PlayerEntity(world, playerTexture, new Vector2(1,8));
        for (int i = 0; i < 1000; i++) {
            floorList.add(new FloorEntity(world, floorTexture,i,1f,1, false));
        }
        for (int i = 0; i < 1000; i++) {
            roofList.add(new FloorEntity(world, roofTexture,i,1f, 16, true));
        }
        //floorList.add(new FloorEntity(world, floorTexture,0,1000,1));
        for (int i = 1; i < 101; i++) {
            gusanoList.add(new GusanoEntity(world, enemyTexture, 15*i,2.45f, false));
            gusanoList.add(new GusanoEntity(world, enemyTexture, 15*i,14f, true));
        }
        //whispList.add(new WhispEntity(world, enemyTexture, 15f, 7, true));
        for (int i = 9; i <45 ; i++) {
            if (i<19) {
                whispList.add(new WhispEntity(world, enemyTexture, (15 * i) + 7.5f, 8, false));
            }else{
                whispList.add(new WhispEntity(world, enemyTexture, (15 * i) + 7.5f, 8, true));
            }
        }

        stage.addActor(player);
        for(FloorEntity floor : floorList){
            stage.addActor(floor);
        }
        for(FloorEntity roof : roofList){
            stage.addActor(roof);
        }
        for(GusanoEntity gusano : gusanoList){
            stage.addActor(gusano);
        }
        for(WhispEntity whisp : whispList){
            stage.addActor(whisp);
        }
    }

    @Override
    public void hide() {
        for(FloorEntity floor : floorList){
            floor.detach();
            floor.remove();
        }
        for(FloorEntity roof : roofList){
            roof.detach();
            roof.remove();
        }
        for(GusanoEntity gusano : gusanoList){
            gusano.detach();
            gusano.remove();
        }
        for(WhispEntity whisp : whispList){
            whisp.detach();
            whisp.remove();
        }
        player.detach();
        player.remove();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.063f,0.131f,0.191f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (player.getX()>150 && player.isAlive()) {
            stage.getCamera().translate(VELOCITY_X * delta * PIXELS_IN_METER, 0, 0);
        }
        stage.act();
        world.step(delta, 6,2);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }
}
