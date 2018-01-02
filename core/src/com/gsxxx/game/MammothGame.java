package com.gsxxx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class MammothGame extends ApplicationAdapter {

    private EndlessScrollingBackground background;
    private Mammoth mammoth;
    private Ribbon ribbon;
    private Panel panel;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;


    @Override
    public void create() {
        camera = new OrthographicCamera();
        ribbon = new Ribbon();
        //gestures listener initialization
        Gdx.input.setInputProcessor(new GestureDetector(new OverriddenGesturesListener(ribbon)));

        background = new EndlessScrollingBackground();
        mammoth = new Mammoth();
        panel = new Panel();

        initializePhysics();
    }

    @Override
    public void render() {
        background.render();
        mammoth.render();
        ribbon.render();
        panel.render();

        debugRenderer.render(world, camera.combined);
        world.step(1 / 60f, 6, 2);
    }

    @Override
    public void dispose() {
        background.dispose();
        mammoth.dispose();
        panel.dispose();
    }

    void initializePhysics() {
        this.world = new World(new Vector2(0, -10), true);
        debugRenderer = new Box2DDebugRenderer();
    }
}
