package com.gsxxx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.LinkedList;

public class MyContactListener implements ContactListener {

    private LinkedList<StickInfo> thingsToStick;

    MyContactListener(LinkedList<StickInfo> thingsToStick) {
        this.thingsToStick = thingsToStick;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Body bodyA = fixtureA.getBody();
        Body bodyB = fixtureB.getBody();

        if (bodyA.getUserData().equals("mammoth") && bodyB.getUserData().equals("spearHead")) {
            thingsToStick.add(new StickInfo(bodyA, bodyB, contact.getWorldManifold().getPoints()));
            Mammoth.getInstance().mammothGotHit();
        } else if (bodyA.getUserData().equals("spearHead") && bodyB.getUserData().equals("mammoth")) {
            thingsToStick.add(new StickInfo(bodyB, bodyA, contact.getWorldManifold().getPoints()));
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

    class StickInfo{
        private Body mammoth;
        private Body projectile;
        private Vector2[] contactPoints;

        StickInfo(Body mammoth, Body projectile, Vector2[] contactPoints) {
            this.mammoth = mammoth;
            this.projectile = projectile;
            this.contactPoints = contactPoints;
        }

        public Body getMammoth() {
            return mammoth;
        }

        public Body getProjectile() {
            return projectile;
        }

        public Vector2[] getContactPoints() {
            return contactPoints;
        }
    }
}
