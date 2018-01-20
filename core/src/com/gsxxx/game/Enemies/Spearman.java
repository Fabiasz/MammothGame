package com.gsxxx.game.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gsxxx.game.MammothGame;

public class Spearman extends Enemy {
    //assurance of single instantiation
    private static boolean instantiated_ = false;

    //spearman look variables
    private SpriteBatch batch;
    private Animation<TextureRegion> ThrowAnimation;
    private Texture spearmanIdle;
    private Texture spearmanDead;

    private float stateTime;

    //enemy image properties variables
    private float enemyImagePositionX;
    private float enemyImagePositionY;
    private float enemyImageWidth;
    private float enemyImageHeight;

    private float deadEnemyImageWidth;
    private float deadEnemyImagePositionY;


    //spearman states
    public enum enemyStates {
        STATE_IDLE,
        STATE_ACTIVE,
        STATE_DEAD,
    }

    private static enemyStates enemyState = enemyStates.STATE_ACTIVE;

    public Spearman() {

        assert (!instantiated_);
        instantiated_ = true;

        batch = new SpriteBatch();
        batch.setProjectionMatrix(MammothGame.camera.combined);

        //counting animation time
        stateTime = 0f;

        //setting variables
        enemyImagePositionX = 6.3f;
        enemyImagePositionY = 1.15f;
        enemyImageWidth = 1.75f;
        enemyImageHeight = 1.9f;
        deadEnemyImageWidth = 1.9f;
        deadEnemyImagePositionY = 0.9f;
        float AnimationTimeDuration = 0.1f;

        TextureRegion[] throwFrames = new TextureRegion[5];
        int index = 0;
        for (int i = 1; i < 6; i++) {
            throwFrames[index++] = new TextureRegion(new Texture("spearman/spearman" + i + ".png"));
        }
        ThrowAnimation = new Animation<TextureRegion>(AnimationTimeDuration, throwFrames);

        //idle spearman
        spearmanIdle = new Texture("spearman/spearman3.png");
        spearmanDead = new Texture("spearman/spearmanDead.png");
    }

    public void render() {
        switch (this.getEnemyState()) {
            case STATE_ACTIVE:
                enemyThrowingAnimation();
                break;
            case STATE_IDLE:
                batch.begin();
                batch.draw(spearmanIdle,enemyImagePositionX,enemyImagePositionY,enemyImageWidth, enemyImageHeight);
                batch.end();
            case STATE_DEAD:
                batch.begin();
                batch.draw(spearmanDead,enemyImagePositionX,deadEnemyImagePositionY,deadEnemyImageWidth, enemyImageHeight);
                batch.end();
        }

    }

    public void dispose() {
        instantiated_ = false;
        batch.dispose();
    }
    public enemyStates getEnemyState() {
        return enemyState;
    }

    public void setEnemyState(enemyStates enemyState) {
        Spearman.enemyState = enemyState;
    }

    private void enemyThrowingAnimation() {
        stateTime += Gdx.graphics.getDeltaTime();
        batch.begin();
        batch.draw(ThrowAnimation.getKeyFrame(stateTime, true), enemyImagePositionX, enemyImagePositionY, enemyImageWidth, enemyImageHeight);
        batch.end();
    }
}
