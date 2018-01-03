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
import com.gsxxx.game.projectiles.Spear;

public class MammothGame extends ApplicationAdapter {
    //main objects
    private Panel panel;
    private EndlessScrollingBackground background;
    private Mammoth mammoth;
    private Ribbon ribbon;

    //physics
    public static World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    Spear spear;

    @Override
    public void create() {
        initializePhysics();
        camera = new OrthographicCamera();
        ribbon = new Ribbon();
        //gestures listener initialization
        Gdx.input.setInputProcessor(new GestureDetector(new OverriddenGesturesListener(ribbon)));

        background = new EndlessScrollingBackground();
        mammoth = new Mammoth();
        panel = new Panel();

        spear = new Spear(800, 400);
    }

    @Override
    public void render() {
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        background.render();
        mammoth.render();
        ribbon.render();
        panel.render();

        spear.render();
//        debugRenderer.render(world, camera.combined);
    }

    @Override
    public void dispose() {
        background.dispose();
        mammoth.dispose();
        panel.dispose();
        world.dispose();

        spear.dispose();
    }

    private void initializePhysics() {
        Box2D.init();
        world = new World(new Vector2(0f, -100f), true);
        debugRenderer = new Box2DDebugRenderer();
    }
}
