package com.gsxxx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.utils.Timer;
import com.gsxxx.game.Enemies.Spearman;
import com.gsxxx.game.projectiles.ProjectilePrototype;

import java.util.LinkedList;
import java.util.Random;

public class PlayScreen implements Screen {
    //reference to the game
    MammothGame game;
    //starting objects
    private Spearman spearman;
    private Ribbon ribbon;

    public static World world;
    public static LinkedList<ProjectilePrototype> projectilesToRender;
    public static LinkedList<ProjectilePrototype> projectilesToDestroy;
    private Box2DDebugRenderer debugRenderer;
    static public OrthographicCamera camera;
    private LinkedList<MyContactListener.StickInfo> thingsToStick;

    private boolean isSpearmanTimerSet = false;

    //collision filter constants
    public static final short GROUND = 0x0002;
    public static final short MAMMOTH = 0x0004;
    public static final short SPEAR_HEAD = 0x0008;
    public static final short SPEAR_SHAFT = 0x0010;
    public static final short RIBBON = 0x0020;

    public PlayScreen(MammothGame game) {
        this.game = game;

        initializePhysics();
        initializeCamera();
        Ground ground = Ground.getInstance();//??

        //initialize objects
        ribbon = new Ribbon();
        spearman = new Spearman();

        Gdx.input.setInputProcessor(new GestureDetector(new MyGesturesListener(ribbon)));
    }

    @Override
    public void render(float delta) {
        EndlessScrollingBackground.getInstance().render();
        spearman.render();
        ribbon.render();
        for (ProjectilePrototype projectile : projectilesToRender) {
            projectile.render();
        }
        Panel.getInstance().render();
        Mammoth.getInstance().render();

        debugRenderer.render(world, camera.combined);

        removeUnneededSpears();
        stickProjectileToMammoth();
        spearmanStateUpdate();

        world.step(1 / 45f, 6, 2);
        if (Panel.getInstance().countDown < 0) {
            game.setScreen(new MenuScreen(game));
            Panel.getInstance().countDown = 10;
            Mammoth.getInstance().health=1f;
        }
    }


    private void spearmanStateUpdate() {
        if (!isSpearmanTimerSet) {
            isSpearmanTimerSet = true;
            Random rand = new Random();
            float randomNum = rand.nextFloat() * (2.5f - 1) + 1;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    spearman.shoot();
                    isSpearmanTimerSet = false;
                }
            }, randomNum);
        }
    }

    private void initializePhysics() {
        Box2D.init();
        thingsToStick = new LinkedList<MyContactListener.StickInfo>();
        projectilesToRender = new LinkedList<ProjectilePrototype>();
        projectilesToDestroy = new LinkedList<ProjectilePrototype>();
        world = new World(new Vector2(0f, -9.81f), false);
        world.setContactListener(new MyContactListener(thingsToStick));
        debugRenderer = new Box2DDebugRenderer();
    }

    private void initializeCamera() {
        final int PPM = 200; // physic world scale variable
        camera = new OrthographicCamera((float) Gdx.graphics.getWidth() / PPM, (float) Gdx.graphics.getHeight() / PPM);
        camera.position.set((float) Gdx.graphics.getWidth() / PPM / 2, (float) Gdx.graphics.getHeight() / PPM / 2, 0);
        camera.update();
    }

    private void stickProjectileToMammoth() {
        while (thingsToStick.size() > 0) {
            WeldJointDef weldJointDef = new WeldJointDef();
            weldJointDef.bodyA = thingsToStick.get(0).getMammoth();
            weldJointDef.bodyB = thingsToStick.get(0).getProjectile();
            weldJointDef.collideConnected = true;
            weldJointDef.frequencyHz = 0;
            weldJointDef.dampingRatio = 0;
            weldJointDef.referenceAngle = weldJointDef.bodyB.getAngle() - weldJointDef.bodyA.getAngle();
            weldJointDef.initialize(thingsToStick.get(0).getMammoth(), thingsToStick.get(0).getProjectile(), thingsToStick.get(0).getContactPoints()[0]);
            PlayScreen.world.createJoint(weldJointDef);
            thingsToStick.remove(0);
        }
    }

    private void removeUnneededSpears() {
        while (projectilesToDestroy.size() > 0) {
            projectilesToDestroy.get(0).dispose();
            projectilesToDestroy.remove(0);
        }
    }

    @Override
    public void show() {

    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        EndlessScrollingBackground.getInstance().dispose();
        Mammoth.getInstance().dispose();
        Panel.getInstance().dispose();
        spearman.dispose();
        for (ProjectilePrototype projectile : projectilesToRender) {
            projectile.dispose();
        }
        removeUnneededSpears();
        world.dispose();
    }
}
