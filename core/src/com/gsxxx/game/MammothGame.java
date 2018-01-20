package com.gsxxx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.gsxxx.game.Enemies.Spearman;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.gsxxx.game.projectiles.ProjectilesPrototype;
import com.gsxxx.game.projectiles.Spear;

import java.util.LinkedList;

import static com.badlogic.gdx.Input.Keys.*;
import static com.gsxxx.game.Enemies.Spearman.enemyStates.STATE_ACTIVE;
import static com.gsxxx.game.Enemies.Spearman.enemyStates.STATE_DEAD;
import static com.gsxxx.game.Enemies.Spearman.enemyStates.STATE_IDLE;
import static com.gsxxx.game.Mammoth.MammothStates.STATE_RUNNING;
import static com.gsxxx.game.Mammoth.MammothStates.STATE_STRUCK;


public class MammothGame extends ApplicationAdapter {
    //main objects
    private Panel panel;
    private EndlessScrollingBackground background;
    private Mammoth mammoth;
    private Spearman spearman;
    private Ribbon ribbon;
    private Ground ground;

    //physics
    public static World world;
    private Box2DDebugRenderer debugRenderer;
    static public OrthographicCamera camera;
    private LinkedList<MyContactListener.StickInfo> thingsToStick;
    public static LinkedList<ProjectilesPrototype> projectilesToRender;

    Spear spear;

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
        spearman = new Spearman();
        panel = new Panel();
//        spear = new Spear(7, 4, 45);
        ground = new Ground();
    }

    @Override
    public void render() {
        background.render();
        mammoth.render();
        spearman.render();
        ribbon.render();

//        spear.render();
        for (ProjectilesPrototype projectile : projectilesToRender) {
            projectile.render();
        }
        panel.render(mammoth.health);

        debugRenderer.render(world, camera.combined);

        mammothStateUpdate();
        spearmanStateUpdate();
        world.step(1 / 45f, 6, 2);

        stickProjectileToMammoth();
    }

    @Override
    public void dispose() {
        background.dispose();
        mammoth.dispose();
        spearman.dispose();
        panel.dispose();
        world.dispose();
    }

    private void mammothStateUpdate() {
        //press P demo collision
        if (Gdx.input.isKeyJustPressed(P) && mammoth.health > 0) {
            mammoth.setState(STATE_STRUCK);
            mammoth.health -= 0.1;
            if (panel.colorRed < 1) {
                panel.colorRed += 0.1;
            } else {
                panel.colorGreen += 0.2;
                panel.colorBlue += 0.2;
            }
        } else {
            mammoth.setState(STATE_RUNNING);
        }
    }

    private void spearmanStateUpdate() {
        //press L demo collision
        if (Gdx.input.isKeyPressed(L)) {
            spearman.setEnemyState(STATE_DEAD);
        } else if (Gdx.input.isKeyPressed(O)) {
            spearman.setEnemyState(STATE_ACTIVE);
        } else if (Gdx.input.isKeyPressed(U)) {
            spearman.shoot();
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
}
