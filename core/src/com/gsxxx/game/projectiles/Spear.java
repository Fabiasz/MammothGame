package com.gsxxx.game.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.gsxxx.game.MammothGame;

public class Spear extends ProjectilesPrototype {

    private SpriteBatch batch;
    private Sprite projectileSprite;

    private Body spearHead;
    private Body spearShaft;

    public Spear(float projectileStartingPositionX, float projectileStartingPositionY, int projectileStartingAngle) {
        //spear look
        batch = new SpriteBatch();
        batch.setProjectionMatrix(MammothGame.camera.combined);
        projectileSprite = new Sprite(new Texture("spear.png"));
        // 2 m x 0.3 m
        projectileSprite.setSize(2f, 0.3f);

        //spear shaft body
        BodyDef spearBodyDefShaft = new BodyDef();
        spearBodyDefShaft.type = BodyDef.BodyType.DynamicBody;
        spearBodyDefShaft.position.set(projectileStartingPositionX, projectileStartingPositionY);
        spearShaft = MammothGame.world.createBody(spearBodyDefShaft);

        //shaft hitbox
        PolygonShape spearHitboxShaft = new PolygonShape();
        spearHitboxShaft.setAsBox(projectileSprite.getWidth() / 2 * 859 / 1109, projectileSprite.getHeight() / 4);

        //shaft fixture
        FixtureDef fixtureDefShaft = new FixtureDef();
        fixtureDefShaft.shape = spearHitboxShaft;
        fixtureDefShaft.density = 500f;
        fixtureDefShaft.friction = 0f;
        fixtureDefShaft.restitution = 0f; // make it not bounce at all
        fixtureDefShaft.filter.categoryBits = 0x0002;
        fixtureDefShaft.filter.maskBits = 0x0001;

        //spear head body
        BodyDef spearBodyDefHead = new BodyDef();
        spearBodyDefHead.type = BodyDef.BodyType.DynamicBody;
        spearBodyDefHead.position.set(projectileStartingPositionX - projectileSprite.getWidth() / 2, projectileStartingPositionY);
        spearHead = MammothGame.world.createBody(spearBodyDefHead);

        //head hitbox
        PolygonShape spearHitboxHead = new PolygonShape();

        Vector2[] verticesShaft = new Vector2[4];
        verticesShaft[0] = new Vector2(-projectileSprite.getWidth() / 2 * 250 / 1109, 0);
        verticesShaft[1] = new Vector2(0, projectileSprite.getHeight() / 2);
        verticesShaft[2] = new Vector2(projectileSprite.getWidth() / 2 * 250 / 1109, 0);
        verticesShaft[3] = new Vector2(0, -projectileSprite.getHeight() / 2);
        spearHitboxHead.set(verticesShaft);

        //head fixture
        FixtureDef fixtureDefHead = new FixtureDef();
        fixtureDefHead.shape = spearHitboxHead;
        fixtureDefHead.density = 7800f;
        fixtureDefHead.friction = 100f;
        fixtureDefHead.restitution = 0f; // make it not bounce at all
        fixtureDefHead.filter.categoryBits = 0x0002;
        fixtureDefHead.filter.maskBits = 0x0001;


        //apply fixtures to body
        spearHead.createFixture(fixtureDefHead);
        spearShaft.createFixture(fixtureDefShaft);
        spearHead.setUserData("spearHead");
        spearShaft.setUserData("spearShaft");

        //dispose hitbox
        spearHitboxHead.dispose();
        spearHitboxShaft.dispose();


        //weld the spear head to the shaft
        WeldJointDef weldJointDef = new WeldJointDef();
        weldJointDef.bodyA = spearHead;
        weldJointDef.bodyB = spearShaft;
        weldJointDef.type = JointDef.JointType.WeldJoint;
        weldJointDef.collideConnected = false;
        weldJointDef.frequencyHz = 0;
        weldJointDef.dampingRatio = 0;
        Vector2 weldPoint = spearHead.getWorldCenter();
        weldJointDef.initialize(weldJointDef.bodyB, weldJointDef.bodyA, weldPoint);
        MammothGame.world.createJoint(weldJointDef);

        //set origin point for sprite
        projectileSprite.setOrigin(projectileSprite.getWidth() * 680 / 1109, projectileSprite.getHeight() / 2);

        setSpearAngle(projectileStartingAngle);
        turnOffGravityForThisSpear();

        //apply force to spear
//       spearHead.applyForceToCenter(0.0f, -100.0f, true);
//       spearHead.applyLinearImpulse(new Vector2(-800,-600), spearHead.getWorldCenter(), true);

    }

    public void render() {
        batch.begin();
        projectileSprite.setRotation((float) Math.toDegrees(spearShaft.getAngle()));
        projectileSprite.setPosition(spearShaft.getPosition().x - projectileSprite.getWidth() / 2 - projectileSprite.getWidth() / 2 * 250 / 1109,
                spearShaft.getPosition().y - projectileSprite.getHeight() / 2);
        projectileSprite.draw(batch);
        batch.end();
    }

    public void wake() {
        spearHead.setGravityScale(1);
        spearShaft.setGravityScale(1);
    }

    private void turnOffGravityForThisSpear() {
        spearHead.setGravityScale(0);
        spearShaft.setGravityScale(0);
    }

    public void shoot() {
        spearHead.applyLinearImpulse(new Vector2(-7000, 0), spearHead.getWorldCenter(), true);
    }

    private void setSpearAngle(int projectileStaringAngle) {
        //r distance between head starting position and shaft starting position needed to set starting angle
        float r = (float) Math.sqrt(Math.pow(spearShaft.getPosition().x - spearHead.getPosition().x, 2) + Math.pow(spearShaft.getPosition().y - spearHead.getPosition().y, 2));
        spearShaft.setTransform(spearShaft.getPosition(), (float) Math.toRadians(projectileStaringAngle));
        spearHead.setTransform(spearShaft.getPosition().x - (float) Math.cos(Math.toRadians(projectileStaringAngle)) * r,
                spearShaft.getPosition().y + (float) Math.sin(Math.toRadians(projectileStaringAngle)) * r,
                (float) Math.toRadians(projectileStaringAngle));
    }

    /*
    to integrate spear with graphics
     */
    public void setSpearPosition(float x, float y) {
            Vector2 oldShaftPosition = spearShaft.getPosition();
            Vector2 oldHeadPosition = spearHead.getPosition();

            Vector2 difference = new Vector2(x - oldShaftPosition.x, y - oldShaftPosition.y);
            spearShaft.setTransform(x, y, spearShaft.getAngle());
            spearHead.setTransform(oldHeadPosition.x + difference.x, oldHeadPosition.y + difference.y, spearHead.getAngle());
     }

    public void dispose() {
        batch.dispose();
    }
}
