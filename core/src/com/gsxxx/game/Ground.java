package com.gsxxx.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public final class Ground {
    private static final Ground INSTANCE = new Ground();

    private Ground() {
        BodyDef groundDef = new BodyDef();
        groundDef.position.set(MammothGame.camera.viewportWidth / 2, 1);

        Body groundBody = MammothGame.world.createBody(groundDef);
        groundBody.setUserData("ground");

        PolygonShape groundBox = new PolygonShape();

        groundBox.setAsBox(MammothGame.camera.viewportWidth / 2, 0);
        groundBody.createFixture(groundBox, 0);
    }

    public static Ground getInstance() {
        return INSTANCE;
    }
}
