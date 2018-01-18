package com.gsxxx.game;

import com.badlogic.gdx.physics.box2d.*;

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
//        if (bodyA.getUserData().equals("mammoth") && bodyB.getUserData().equals("spearHead")) {
//
//        } else if (bodyB.getUserData().equals("mammoth") && bodyA.getUserData().equals("spearHead")) {
//
//        }
    }
}
