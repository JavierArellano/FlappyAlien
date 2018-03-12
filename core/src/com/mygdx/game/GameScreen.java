package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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

    private Stage stage, stage2;
    private World world;
    private PlayerEntity player;
    private List<FloorEntity> floorList = new ArrayList<FloorEntity>();
    private List<FloorEntity> roofList = new ArrayList<FloorEntity>();
    private List<GusanoEntity> gusanoList = new ArrayList<GusanoEntity>();
    private List<WhispEntity> whispList = new ArrayList<WhispEntity>();
    private Sound saltoSound, dieSound;
    private Music bgMusic;
    private Vector3 position;
    private Label puntos;
    private Skin skin;
    private int puntuacion;
    private float posi=10*PIXELS_IN_METER;

    public GameScreen(final MainGame game) {
        super(game);
        stage = new Stage(new FitViewport(1120,630));
        stage2 = new Stage(new FitViewport(1120,630));
        position = new Vector3(stage.getCamera().position);

        world = new World(new Vector2(0,-10), true);
        world.setContactListener(new ContactListener() {
            private boolean areCollided(Contact contact, Object userA, Object userB){
                return contact.getFixtureA().getUserData().equals(userA)&& contact.getFixtureB().getUserData().equals(userB) ||
                        contact.getFixtureA().getUserData().equals(userB) && contact.getFixtureB().getUserData().equals(userA);
            }
            @Override
            public void beginContact(Contact contact) {

                if (areCollided(contact, "player", "floor")) {
                    if (player.isAlive()) {
                        dieSound.play();
                        player.setAlive(false);
                    }
                }

                if (areCollided(contact, "player", "gusano")) {
                    if (player.isAlive()) {
                        dieSound.play();
                        player.setAlive(false);
                    }
                }
                if (areCollided(contact, "player", "mosca")) {
                    if (player.isAlive()) {
                        dieSound.play();
                        player.setAlive(false);
                    }
                }
                if (!player.isAlive() && player.isCambio()) {
                    player.setCambio(false);
                    stage.addAction(
                            Actions.sequence(
                                    Actions.delay(1.5f),
                                    Actions.run(new Runnable() {
                                        @Override
                                        public void run() {
                                            bgMusic.stop();
                                            game.setScreen(game.gameOverScreen);
                                        }
                                    })
                            )
                    );
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

        saltoSound = game.getManager().get("audio/salto.mp3");
        dieSound = game.getManager().get("audio/dolorsito.mp3");
        bgMusic = game.getManager().get("audio/music.wav");

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
    }

    @Override
    public void show() {
        Texture playerTexture = game.getManager().get("players.png");
        Texture enemyTexture = game.getManager().get("enemies.png");
        Texture floorTexture = game.getManager().get("floor.png");
        Texture roofTexture = game.getManager().get("roof.png");

        puntos = new Label("0", skin);
        puntuacion = 0;
        puntos.setFontScale(1.5f);
        puntos.setColor(Color.RED);


        stage.getCamera().position.set(stage.getWidth() / 2, stage.getHeight() / 2, 0);

        player = new PlayerEntity(world, playerTexture, new Vector2(1,7.5f));

        for (int i = 0; i < 500; i++) {
            floorList.add(new FloorEntity(world, floorTexture,i,1f,1, false));
        }
        for (int i = 0; i < 500; i++) {
            roofList.add(new FloorEntity(world, roofTexture,i,1f, 16, true));
        }
        for (int i = 1; i < 35; i++) {
            gusanoList.add(new GusanoEntity(world, enemyTexture, 10*i,2.45f, false));
            gusanoList.add(new GusanoEntity(world, enemyTexture, 10*i,14f, true));
        }
        for (int i = 9; i < 30 ; i++) {
            if (i<19) {
                whispList.add(new WhispEntity(world, enemyTexture, (10 * i) + 5f, 8, false));
            }else{
                if (i%2==0) {
                    whispList.add(new WhispEntity(world, enemyTexture, (10 * i) + 5f, 13, true));
                }else{
                    whispList.add(new WhispEntity(world, enemyTexture, (10 * i) + 5f, 2, true));
                }
            }
        }

        stage2.addActor(puntos);
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

        stage.getCamera().position.set(position);
        stage.getCamera().update();

        bgMusic.setVolume(0.25f);
        bgMusic.play();
    }

    @Override
    public void hide() {
        stage.clear();
        stage2.clear();

        for(FloorEntity floor : floorList){
            floor.detach();
        }
        for(FloorEntity roof : roofList){
            roof.detach();
        }
        for(GusanoEntity gusano : gusanoList){
            gusano.detach();
        }
        for(WhispEntity whisp : whispList){
            whisp.detach();
        }
        player.detach();

        floorList.clear();
        roofList.clear();
        gusanoList.clear();
        whispList.clear();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.063f,0.131f,0.191f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (player.getX()>150 && player.isAlive()) {
            stage.getCamera().translate(VELOCITY_X * delta * PIXELS_IN_METER, 0, 0);

            puntos.setPosition(2*PIXELS_IN_METER, 15.4f*PIXELS_IN_METER);
        }
        if (player.isAlive()){
            if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.UP)){
                saltoSound.play();
                player.saltar();
            }
            if (player.getX()>=posi){
                posi+=10*PIXELS_IN_METER;
                puntuacion++;
                String pt = String.valueOf(puntuacion);
                puntos.setText(pt);
            }
        }
        stage.act();
        world.step(delta, 6,2);
        stage.draw();
        stage2.act();
        stage2.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        stage2.dispose();
        world.dispose();
    }
}
