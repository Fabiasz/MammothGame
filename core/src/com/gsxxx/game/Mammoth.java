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

import static com.badlogic.gdx.Input.Keys.P;
import static com.gsxxx.game.Mammoth.MammothStates.STATE_STRUCK;

public class Mammoth {
    //assurance of single instantiation
    private static boolean instantiated_ = false;

    //mammoth look variables
    private SpriteBatch batch;
    private Animation<TextureRegion> mammothRunAnimation;
    //private Animation<TextureRegion> mammothStruckAnimation;  TODO: solve struck mammoth time
    private Texture mammothStruck;
    //private TextureAtlas atlas; todo
    private float stateTime;

    //mammoth image properties variables
    private float mammothImagePositionX;
    private float mammothImagePositionY;
    private float mammothImageWidth;
    private float mammothImageHeight;
    float health = 0.8f;
    private Body mammothBody;

    //mammoth states
    public enum MammothStates {
        STATE_RUNNING,
        STATE_STRUCK,
    }

    private static MammothStates mammothState = MammothStates.STATE_RUNNING;

    public static MammothStates getState() {
        return mammothState;
    }

    public static void setState(MammothStates state) {
        mammothState = state;
    }


    Mammoth() {
        assert (!instantiated_);
        instantiated_ = true;

        batch = new SpriteBatch();
        //counting animation time
        stateTime = 0f;

        //setting variables
        mammothImagePositionX = 0.3f;
        mammothImagePositionY = 1.2f;
        mammothImageWidth = 3f;
        mammothImageHeight = 1.87f;
        float runningAnimationFrameDuration = 0.1f;


        //setting up mammoth's hitbox
        BodyDef mammothBodyDef = new BodyDef();
        mammothBodyDef.type = BodyDef.BodyType.StaticBody;
        mammothBodyDef.position.set(mammothImagePositionX + mammothImageWidth / 2, mammothImagePositionY + mammothImageHeight / 2);
        mammothBody = MammothGame.world.createBody(mammothBodyDef);
        PolygonShape mammothHitBox = new PolygonShape();
        mammothHitBox.setAsBox(mammothImageWidth / 2, mammothImageHeight / 2); //todo make hitbox more precise
        mammothBody.createFixture(mammothHitBox, 0.0f);
        mammothHitBox.dispose();

        //packing running animation frames
        TextureRegion[] walkFrames = new TextureRegion[4];
        int index = 0;
        for (int i = 1; i < 5; i++) {
            walkFrames[index++] = new TextureRegion(new Texture("mammoth/runningMammoth_" + i + ".png"));
        }
        mammothRunAnimation =
                new Animation<TextureRegion>(runningAnimationFrameDuration, walkFrames);

        mammothStruck = new Texture("mammoth/struckMammoth_1.png");
    }

    void render() {
        if (getState().equals(STATE_STRUCK)) {
            batch.begin();
            batch.draw(mammothStruck, mammothBody.getPosition().x - mammothImageWidth / 2,
                    mammothBody.getPosition().y - mammothImageHeight / 2, mammothImageWidth, mammothImageHeight);
            batch.end();
        } else {
            mammothRunningAnimation();
        }
    }

    void dispose() {
        instantiated_ = false;
        batch.dispose();
    }

    private void mammothRunningAnimation() {
        stateTime += Gdx.graphics.getDeltaTime();
        batch.begin();
        batch.setProjectionMatrix(MammothGame.camera.combined);
        batch.draw(mammothRunAnimation.getKeyFrame(stateTime, true), mammothBody.getPosition().x - mammothImageWidth / 2,
                mammothBody.getPosition().y - mammothImageHeight / 2, mammothImageWidth, mammothImageHeight);
        batch.end();
    }

}