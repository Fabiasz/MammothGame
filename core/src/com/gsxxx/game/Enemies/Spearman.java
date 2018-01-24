package com.gsxxx.game.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gsxxx.game.MammothGame;
import com.gsxxx.game.projectiles.Spear;

public final class Spearman extends Enemy {
    //spearman look variables
    private SpriteBatch batch;
    private Animation<TextureRegion> throwAnimation;
    private Texture spearmanIdle;
    private Texture spearmanDead;

    private float stateTime;

    //enemy image properties variables
    private float enemyImagePositionX;
    private float enemyImagePositionY;
    private float enemyImageWidth;
    private float deadEnemyImageWidth;
    private float enemyImageHeight;

    private Spear spear;

    //spearman states
    public enum enemyStates {
        STATE_IDLE,
        STATE_SHOOTING,
        STATE_DEAD,
    }

    private boolean hasSpear = false;

    private enemyStates enemyState = enemyStates.STATE_IDLE;
    private Vector2[] positionOfSpearOverTime;

    public Spearman() {

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
        float animationTimeDuration = 0.1f;

        TextureRegion[] throwFrames = new TextureRegion[5];
        int index = 0;
        for (int i = 1; i < 6; i++) {
            throwFrames[index++] = new TextureRegion(new Texture("spearman/spearman" + i + ".png"));
        }

        throwAnimation = new Animation<TextureRegion>(animationTimeDuration, throwFrames);

        spearmanIdle = new Texture("spearman/spearman3.png");
        spearmanDead = new Texture("spearman/spearmanDead.png");

        positionOfSpearOverTime = new Vector2[]{
                new Vector2(enemyImagePositionX + enemyImageWidth * 67 / 80, enemyImagePositionY + enemyImageHeight * 40 / 90),
                new Vector2(enemyImagePositionX + enemyImageWidth * 62 / 80, enemyImagePositionY + enemyImageHeight * 48 / 90),
                new Vector2(enemyImagePositionX + enemyImageWidth * 55 / 80, enemyImagePositionY + enemyImageHeight * 55 / 90),
                new Vector2(enemyImagePositionX + enemyImageWidth * 40 / 80, enemyImagePositionY + enemyImageHeight * 60 / 90),
                new Vector2(enemyImagePositionX + enemyImageWidth * 18 / 80, enemyImagePositionY + enemyImageHeight * 62 / 90)};

    }

    public void render() {
        batch.begin();
        switch (this.getEnemyState()) {
            case STATE_SHOOTING:
                enemyThrowingAnimation();
                break;
            case STATE_IDLE:
                batch.draw(spearmanIdle, enemyImagePositionX, enemyImagePositionY, enemyImageWidth, enemyImageHeight);
                break;
            case STATE_DEAD:
                batch.draw(spearmanDead, enemyImagePositionX, enemyImagePositionY, deadEnemyImageWidth, enemyImageHeight);
                break;
        }
        batch.end();

        if (!hasSpear) {
            createSpear();
        }
    }

    private enemyStates getEnemyState() {
        return enemyState;
    }

    private void setEnemyState(enemyStates enemyState) {
        this.enemyState = enemyState;
        stateTime = 0;
    }

    private void enemyThrowingAnimation() {
        stateTime += Gdx.graphics.getDeltaTime();
        batch.draw(throwAnimation.getKeyFrame(stateTime, false), enemyImagePositionX, enemyImagePositionY,
                enemyImageWidth, enemyImageHeight);
        spear.setSpearPosition(positionOfSpearOverTime[throwAnimation.getKeyFrameIndex(stateTime)].x,
                positionOfSpearOverTime[throwAnimation.getKeyFrameIndex(stateTime)].y);
        if (throwAnimation.isAnimationFinished(stateTime)) {
            setEnemyState(enemyStates.STATE_IDLE);
            spear.wake();
            spear.shoot(8000);
            hasSpear = false;
        }

    }

    public void kill(){
        setEnemyState(enemyStates.STATE_DEAD);
    }

    private void createSpear() {
        if (!hasSpear) {
            spear = new Spear(enemyImagePositionX + enemyImageWidth * 55 / 80,
                    enemyImagePositionY + enemyImageHeight * 55 / 90, 0);
            MammothGame.projectilesToRender.add(spear);
            hasSpear = true;
        } else {
            System.out.println("Wrong spear creating");
        }
    }

    public void shoot() {
        if (hasSpear && !getEnemyState().equals(enemyStates.STATE_SHOOTING)) {
            setEnemyState(enemyStates.STATE_SHOOTING);
        } else {
            System.out.println("no spear or is already shooting");
        }
    }


    public void dispose() {
        batch.dispose();
    }
}
