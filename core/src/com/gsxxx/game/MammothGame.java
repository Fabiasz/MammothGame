package com.gsxxx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.gsxxx.game.Enemies.Spearman;
import com.gsxxx.game.projectiles.ProjectilesPrototype;

import java.util.LinkedList;

import static com.badlogic.gdx.Input.Keys.*;
import static com.gsxxx.game.Enemies.Spearman.enemyStates.STATE_SHOOTING;
import static com.gsxxx.game.Enemies.Spearman.enemyStates.STATE_DEAD;
import static com.gsxxx.game.Mammoth.MammothStates.STATE_RUNNING;
import static com.gsxxx.game.Mammoth.MammothStates.STATE_STRUCK;


public class MammothGame extends ApplicationAdapter {
    //starting objects
    private Spearman spearman;
    private Ribbon ribbon;

    public static World world;
    public static LinkedList<ProjectilesPrototype> projectilesToRender;
    private Box2DDebugRenderer debugRenderer;
    static public OrthographicCamera camera;
    private LinkedList<MyContactListener.StickInfo> thingsToStick;

    private float struckStateTimer = 0;

    @Override
    public void create() {
        initializePhysics();
        initializeCamera();
        Gdx.input.setInputProcessor(new GestureDetector(new MyGesturesListener(ribbon)));

        //initialize objects
        ribbon = new Ribbon();
        spearman = new Spearman();
    }

    @Override
    public void render() {
        mammothStateUpdate();
        spearmanStateUpdate();

        EndlessScrollingBackground.getInstance().render();
        spearman.render();
        ribbon.render();
        for (ProjectilesPrototype projectile : projectilesToRender) {
            projectile.render();
        }
        Panel.getInstance().render(Mammoth.getInstance().health);
        Mammoth.getInstance().render();
        debugRenderer.render(world, camera.combined);

        world.step(1 / 45f, 6, 2);

        stickProjectileToMammoth();
    }

    private void mammothStateUpdate() {
        //press P demo collision
        if (Gdx.input.isKeyPressed(P) && struckStateTimer < 2) {
            Mammoth.getInstance().setState(STATE_STRUCK);
            struckStateTimer += Gdx.graphics.getDeltaTime();
            Mammoth.getInstance().health -= 0.01;
        } else {
            Mammoth.getInstance().setState(STATE_RUNNING);
            struckStateTimer = 0;
        }
    }

    private void spearmanStateUpdate() {
        //press L demo collision
        if (Gdx.input.isKeyPressed(L)) {
            spearman.setEnemyState(STATE_DEAD);
        } else if (Gdx.input.isKeyPressed(O)) {
            spearman.setEnemyState(STATE_SHOOTING);
        } else if (Gdx.input.isKeyPressed(U)) {
            spearman.shoot();
        } else if (Gdx.input.isKeyPressed(Y)) {
            spearman.createSpear();
        }
//        } else {
//            spearman.setEnemyState(STATE_IDLE);
//        }
    }

    private void initializePhysics() {
        Box2D.init();
        thingsToStick = new LinkedList<MyContactListener.StickInfo>();
        projectilesToRender = new LinkedList<ProjectilesPrototype>();
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
            MammothGame.world.createJoint(weldJointDef);
            thingsToStick.remove(0);
        }
    }

    @Override
    public void dispose() {
        EndlessScrollingBackground.getInstance().dispose();
        Mammoth.getInstance().dispose();
        Panel.getInstance().dispose();
        spearman.dispose();
        for (ProjectilesPrototype projectile : projectilesToRender) {
            projectile.dispose();
        }
        world.dispose();
    }
}
