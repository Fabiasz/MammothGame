package com.gsxxx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Mammoth {
    //assurance of single instantiation
    private static boolean instantiated_ = false;

    //mammoth look variables
    private SpriteBatch batch;
    private Animation<TextureRegion> mammothAnimation;

    //private TextureAtlas atlas; todo
    private float stateTime;

    //mammoth image properties variables
    private int mammothImagePositionX;
    private int mammothImagePositionY;
    private int mammothImageWidth;
    private int mammothImageHeight;


    Mammoth() {
        assert (!instantiated_);
        instantiated_ = true;

        batch = new SpriteBatch();
        //counting animation time
        stateTime = 0f;

        //setting variables
        mammothImagePositionX = 50;
        mammothImagePositionY = 250;
        mammothImageWidth = 480;
        mammothImageHeight = 300;
        float runningAnimationFrameDuration = 0.1f;

        //setting up mammoth's hitbox
        BodyDef mammothBodyDef = new BodyDef();
        mammothBodyDef.position.set(mammothImagePositionX, mammothImagePositionY);
        Body mammothBody = MammothGame.world.createBody(mammothBodyDef);
        PolygonShape mammothHitBox = new PolygonShape();
        mammothHitBox.setAsBox(mammothImageWidth, mammothImageHeight); //todo make hitbox more precise
        mammothBody.createFixture(mammothHitBox, 0.0f);
        mammothHitBox.dispose();

        //packing running animation frames
        TextureRegion[] walkFrames = new TextureRegion[6];
        int index = 0;
        for (int i = 1; i < 7; i++) {
            walkFrames[index++] = new TextureRegion(new Texture("mammoth/runningMammoth_" + i + ".png"));
        }
        mammothAnimation =
                new Animation<TextureRegion>(runningAnimationFrameDuration, walkFrames);
    }

    void render() {
        mammothRunningAnimation();
    }

    void dispose() {
        instantiated_ = false;
        batch.dispose();
    }

    private void mammothRunningAnimation() {
        stateTime += Gdx.graphics.getDeltaTime();
        batch.begin();
        batch.draw(mammothAnimation.getKeyFrame(stateTime, true), mammothImagePositionX,
                mammothImagePositionY, mammothImageWidth, mammothImageHeight);
        batch.end();
    }
}
