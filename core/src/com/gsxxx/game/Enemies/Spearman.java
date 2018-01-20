package com.gsxxx.game.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gsxxx.game.MammothGame;
import com.gsxxx.game.projectiles.Spear;

import java.util.Timer;
import java.util.TimerTask;

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
    private float deadEnemyImageWidth;
    private float enemyImageHeight;

    private float deadEnemyImagePositionY;

    private Spear spear;

    //spearman states
    public enum enemyStates {
        STATE_IDLE,
        STATE_ACTIVE,
        STATE_DEAD,
    }

    boolean hasSpear = false;
    boolean isShoooting = false;

    private enemyStates enemyState = enemyStates.STATE_IDLE;
    float animationTimeDuration;

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
        animationTimeDuration = 0.1f;

        TextureRegion[] throwFrames = new TextureRegion[5];
        int index = 0;
        for (int i = 1; i < 6; i++) {
            throwFrames[index++] = new TextureRegion(new Texture("spearman/spearman" + i + ".png"));
        }
        ThrowAnimation = new Animation<TextureRegion>(animationTimeDuration, throwFrames);

        //idle spearman
        spearmanIdle = new Texture("spearman/spearman3.png");
        spearmanDead = new Texture("spearman/spearmanDead.png");

        createSpear();

    }

    public void render() {
        batch.begin();
        switch (this.getEnemyState()) {
            case STATE_ACTIVE:
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
    }

    public void dispose() {
        instantiated_ = false;
        batch.dispose();
    }

    enemyStates getEnemyState() {
        return enemyState;
    }

    public void setEnemyState(enemyStates enemyState) {
        this.enemyState = enemyState;
    }

    private void enemyThrowingAnimation() {
        stateTime += Gdx.graphics.getDeltaTime();
        batch.draw(ThrowAnimation.getKeyFrame(stateTime, true), enemyImagePositionX, enemyImagePositionY,
                enemyImageWidth, enemyImageHeight);
    }

    private void createSpear() {
        if (!hasSpear) {
            spear = new Spear(enemyImagePositionX + enemyImageWidth * 55 / 80,
                    enemyImagePositionY + enemyImageHeight * 55 / 90, 0);
            MammothGame.projectilesToRender.add(spear);
            hasSpear = true;
        }
    }

    public void shoot() {
        if (hasSpear && !isShoooting) {
            isShoooting = true;
            setEnemyState(enemyStates.STATE_ACTIVE);
            Timer timer = new Timer();
            timer.schedule(new Task(spear), 0, (int) (1000 * animationTimeDuration));
        }
    }

    class Task extends TimerTask {
        Spear spear;
        int i;
        Vector2[] positionOfSpearOverTime;

        Task(Spear spear) {
            this.spear = spear;
            i = 0;
            positionOfSpearOverTime = new Vector2[]{
                    new Vector2(enemyImagePositionX + enemyImageWidth * 67 / 80, enemyImagePositionY + enemyImageHeight * 40 / 90),
                    new Vector2(enemyImagePositionX + enemyImageWidth * 62 / 80, enemyImagePositionY + enemyImageHeight * 48 / 90),
                    new Vector2(enemyImagePositionX + enemyImageWidth * 55 / 80, enemyImagePositionY + enemyImageHeight * 55 / 90),
                    new Vector2(enemyImagePositionX + enemyImageWidth * 40 / 80, enemyImagePositionY + enemyImageHeight * 60 / 90),
                    new Vector2(enemyImagePositionX + enemyImageWidth * 18 / 80, enemyImagePositionY + enemyImageHeight * 62 / 90)};
        }

        @Override
        public void run() {
            if (i == 0) spear.wake();
            spear.setSpearPosition(positionOfSpearOverTime[i].x, positionOfSpearOverTime[i].y, 0);
            i++;
            if (i > 4) {
                setEnemyState(enemyStates.STATE_IDLE);

                isShoooting = false;
                hasSpear = false;
                this.cancel();
            }
        }
    }
}
