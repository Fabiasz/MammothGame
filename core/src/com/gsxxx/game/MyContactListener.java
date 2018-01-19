package com.gsxxx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;

public class MyContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Body bodyA = fixtureA.getBody();
        Body bodyB = fixtureB.getBody();


        if (bodyA.getUserData().equals("mammoth") && bodyB.getUserData().equals("spearHead")) {
            stickProjectileAndMammoth(bodyA, bodyB, contact.getWorldManifold().getPoints());
        } else if (bodyA.getUserData().equals("spearHead") && bodyB.getUserData().equals("mammoth")) {
            stickProjectileAndMammoth(bodyB, bodyA, contact.getWorldManifold().getPoints());
        }


    }

    void stickProjectileAndMammoth(Body mammoth, Body projectile, Vector2 contactPoints[]) {
        WeldJointDef weldJointDef = new WeldJointDef();
        weldJointDef.bodyA = mammoth;
        weldJointDef.bodyB = projectile;
        weldJointDef.collideConnected = true;
        weldJointDef.frequencyHz = 0;
        weldJointDef.dampingRatio = 0;
        weldJointDef.referenceAngle = weldJointDef.bodyB.getAngle() - weldJointDef.bodyA.getAngle();
//        weldJointDef.initialize(mammoth, projectile, contactPoints[0]);
//        MammothGame.world.createJoint(weldJointDef);
    }
}
