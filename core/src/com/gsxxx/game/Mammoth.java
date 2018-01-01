package com.gsxxx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Mammoth {
    //single instantiation assurance
    private static boolean instantiated_ = false;
    private SpriteBatch batch;

    private Animation<TextureRegion> mammothAnimation;
    private TextureAtlas atlas; //todo optimize animation using TexturePacker and TextureAtlas
    private float stateTime;

    Mammoth() {
        assert (!instantiated_);
        instantiated_ = true;

        batch = new SpriteBatch();
        stateTime = 0f; //time of animation variable


        //packing animation frames
        TextureRegion[] walkFrames = new TextureRegion[4];
        int index = 0;

        for (int i = 1; i < 5; i++) {
            walkFrames[index++] = new TextureRegion(new Texture("mammoth/m" + i + ".png"));
        }
        mammothAnimation =
                new Animation<TextureRegion>(0.08f, walkFrames);
    }

    void render() {
        stateTime += Gdx.graphics.getDeltaTime();
        batch.begin();
        batch.draw(mammothAnimation.getKeyFrame(stateTime, true), 0, 250, 650, 300);
        batch.end();
    }

    void dispose() {
        instantiated_ = false;
        batch.dispose();
    }
}
