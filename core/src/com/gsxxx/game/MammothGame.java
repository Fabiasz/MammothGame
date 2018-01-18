package com.gsxxx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.gsxxx.game.projectiles.Spear;

import static com.badlogic.gdx.Input.Keys.P;
import static com.gsxxx.game.Mammoth.MammothStates.STATE_RUNNING;
import static com.gsxxx.game.Mammoth.MammothStates.STATE_STRUCK;


public class MammothGame extends ApplicationAdapter {
    //main objects
    private Panel panel;
    private EndlessScrollingBackground background;
    private Mammoth mammoth;
    private Ribbon ribbon;
    private Ground ground;

    //physics
    public static World world;
    private Box2DDebugRenderer debugRenderer;
    static public OrthographicCamera camera;

    private Spear spear;
    private final int PPM = 200;

    @Override
    public void create() {
        initializePhysics();

        camera = new OrthographicCamera((float) Gdx.graphics.getWidth() / PPM, (float) Gdx.graphics.getHeight() / PPM);
        camera.position.set((float) Gdx.graphics.getWidth() / PPM / 2, (float) Gdx.graphics.getHeight() / PPM / 2, 0);
        camera.update();

        ribbon = new Ribbon();
        //gestures listener initialization
        Gdx.input.setInputProcessor(new GestureDetector(new MyGesturesListener(ribbon)));

        background = new EndlessScrollingBackground();
        mammoth = new Mammoth();
        panel = new Panel(mammoth.health);
        spear = new Spear(7, 4, 315);
        ground = new Ground();
    }

    @Override
    public void render() {
        background.render();
        mammoth.render();
        ribbon.render();
        panel.render();
        spear.render();
        debugRenderer.render(world, camera.combined);

        stateUpdate();

        world.step(1 / 45f, 6, 2);
    }

    @Override
    public void dispose() {
        background.dispose();
        mammoth.dispose();
        panel.dispose();
        world.dispose();
        spear.dispose();
    }

    private void stateUpdate() {
        //press P demo collision
        if (Gdx.input.isKeyPressed(P)) {
            mammoth.setState(STATE_STRUCK);
        } else {
            mammoth.setState(STATE_RUNNING);
        }
    }

    private void initializePhysics() {
        Box2D.init();
        world = new World(new Vector2(0f, -9.81f), false);
        world.setContactListener(new MyContactListener());
        debugRenderer = new Box2DDebugRenderer();
    }
}
