package com.gsxxx.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public final class Ground {
    private static final Ground INSTANCE = new Ground();

    private Ground() {
        BodyDef groundDef = new BodyDef();
        groundDef.position.set(MammothGame.camera.viewportWidth / 2, 1.25f);

        Body groundBody = MammothGame.world.createBody(groundDef);
        groundBody.setUserData("ground");

        PolygonShape groundHitBox = new PolygonShape();

        groundHitBox.setAsBox(MammothGame.camera.viewportWidth / 2, 0);
        groundBody.createFixture(groundHitBox, 0);
        groundHitBox.dispose();
    }

    public static Ground getInstance() {
        return INSTANCE;
    }
}
