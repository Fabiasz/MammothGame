package com.gsxxx.game.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.gsxxx.game.PlayScreen;

import static com.gsxxx.game.PlayScreen.*;

public class Spear extends ProjectilePrototype {

    private SpriteBatch batch;
    private Sprite projectileSprite;

    private Body spearHead;
    private Body spearShaft;
    private WeldJoint weldJoint;

    private float lifetime;
    private boolean isWaken;

    public Spear(float projectileStartingPositionX, float projectileStartingPositionY, int projectileStartingAngle) {
        lifetime = 0;
        isWaken = false;
        //spear look
        batch = new SpriteBatch();
        batch.setProjectionMatrix(PlayScreen.camera.combined);
        projectileSprite = new Sprite(new Texture("spear.png"));
        // 2 m x 0.3 m
        projectileSprite.setSize(2f, 0.3f);

        //spear shaft body
        BodyDef spearBodyDefShaft = new BodyDef();
        spearBodyDefShaft.type = BodyDef.BodyType.DynamicBody;
        spearBodyDefShaft.position.set(projectileStartingPositionX, projectileStartingPositionY);
        spearShaft = PlayScreen.world.createBody(spearBodyDefShaft);

        //shaft hitbox
        PolygonShape spearHitboxShaft = new PolygonShape();
        spearHitboxShaft.setAsBox(projectileSprite.getWidth() / 2 * 859 / 1109, projectileSprite.getHeight() / 4);

        //shaft fixture
        FixtureDef fixtureDefShaft = new FixtureDef();
        fixtureDefShaft.shape = spearHitboxShaft;
        fixtureDefShaft.density = 500f;
        fixtureDefShaft.friction = 0f;
        fixtureDefShaft.restitution = 0f; // make it not bounce at all
        fixtureDefShaft.filter.categoryBits = SPEAR_SHAFT;
        fixtureDefShaft.filter.maskBits = GROUND;

        //spear head body
        BodyDef spearBodyDefHead = new BodyDef();
        spearBodyDefHead.type = BodyDef.BodyType.DynamicBody;
        spearBodyDefHead.position.set(projectileStartingPositionX - projectileSprite.getWidth() / 2, projectileStartingPositionY);
        spearHead = PlayScreen.world.createBody(spearBodyDefHead);

        //head hitbox
        PolygonShape spearHitboxHead = new PolygonShape();
        Vector2[] verticesHead = new Vector2[4];
        verticesHead[0] = new Vector2(-projectileSprite.getWidth() / 2 * 250 / 1109, 0);
        verticesHead[1] = new Vector2(0, projectileSprite.getHeight() / 2);
        verticesHead[2] = new Vector2(projectileSprite.getWidth() / 2 * 250 / 1109, 0);
        verticesHead[3] = new Vector2(0, -projectileSprite.getHeight() / 2);
        spearHitboxHead.set(verticesHead);

        //head fixture
        FixtureDef fixtureDefHead = new FixtureDef();
        fixtureDefHead.shape = spearHitboxHead;
        fixtureDefHead.density = 7800f;
        fixtureDefHead.friction = 100f;
        fixtureDefHead.restitution = 0f; // make it not bounce at all
        fixtureDefHead.filter.categoryBits = SPEAR_HEAD;
        fixtureDefHead.filter.maskBits = MAMMOTH | GROUND | RIBBON;

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
        weldJoint = (WeldJoint) PlayScreen.world.createJoint(weldJointDef);

        //set origin point for sprite
        projectileSprite.setOrigin(projectileSprite.getWidth() * 680 / 1109, projectileSprite.getHeight() / 2);

        setSpearAngle(projectileStartingAngle);
        turnOffGravityForThisSpear();
    }

    public void render() {
        if (isWaken) {
            lifetime += Gdx.graphics.getDeltaTime();
        }
        batch.begin();
        projectileSprite.setRotation((float) Math.toDegrees(spearShaft.getAngle()));
        projectileSprite.setPosition(spearShaft.getPosition().x - projectileSprite.getWidth() / 2 - projectileSprite.getWidth() / 2 * 250 / 1109,
                spearShaft.getPosition().y - projectileSprite.getHeight() / 2);
        projectileSprite.draw(batch);
        batch.end();
        if (lifetime > 3) {
            this.destroyThisProjectile();
        }
    }

    public void wake() {
        isWaken = true;
        spearHead.setGravityScale(1);
        spearShaft.setGravityScale(1);
    }

    private void turnOffGravityForThisSpear() {
        isWaken = false;
        spearHead.setGravityScale(0);
        spearShaft.setGravityScale(0);
    }

    public void shoot(int strength) {
        spearShaft.applyLinearImpulse(new Vector2((int) (-Math.cos(spearShaft.getAngle()) * strength),
                (int) (-Math.sin(spearShaft.getAngle()) * strength)), spearShaft.getWorldCenter(), true);
        spearHead.applyLinearImpulse(new Vector2(0, (int) (Math.sin(spearShaft.getAngle()) * strength / 2)),
                spearHead.getWorldCenter(), true);

    }

    private void setSpearAngle(int projectileStaringAngle) {
        //r distance between head starting position and shaft starting position needed to set starting angle
        float r = (float) Math.sqrt(Math.pow(spearShaft.getPosition().x - spearHead.getPosition().x, 2) + Math.pow(spearShaft.getPosition().y - spearHead.getPosition().y, 2));
        spearShaft.setTransform(spearShaft.getPosition(), (float) Math.toRadians(projectileStaringAngle));
        spearHead.setTransform(spearShaft.getPosition().x - (float) Math.cos(Math.toRadians(projectileStaringAngle)) * r,
                spearShaft.getPosition().y - (float) Math.sin(Math.toRadians(projectileStaringAngle)) * r,
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

    public void destroyThisProjectile() {
        PlayScreen.projectilesToDestroy.add(this);
    }

    public void dispose() {
        PlayScreen.world.destroyJoint(weldJoint);
        PlayScreen.world.destroyBody(spearHead);
        PlayScreen.world.destroyBody(spearShaft);
        batch.dispose();
        PlayScreen.projectilesToRender.remove(0);
    }
}
