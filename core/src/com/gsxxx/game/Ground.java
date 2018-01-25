package com.gsxxx.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import static com.gsxxx.game.PlayScreen.*;

public final class Ground {
    private static final Ground INSTANCE = new Ground();

    private Ground() {
        BodyDef groundDef = new BodyDef();
        groundDef.position.set(PlayScreen.camera.viewportWidth / 2, 1.25f);

        Body groundBody = PlayScreen.world.createBody(groundDef);
        groundBody.setUserData("ground");

        PolygonShape groundHitBox = new PolygonShape();

        groundHitBox.setAsBox(PlayScreen.camera.viewportWidth / 2, 0);

        FixtureDef groundFixture = new FixtureDef();
        groundFixture.shape = groundHitBox;
        groundFixture.density = 1f;
        groundFixture.filter.categoryBits = GROUND;
        groundFixture.filter.maskBits = SPEAR_HEAD | SPEAR_SHAFT;
        groundBody.createFixture(groundFixture);
        groundHitBox.dispose();
    }

    public static Ground getInstance() {
        return INSTANCE;
    }
}
