package com.gsxxx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public final class EndlessScrollingBackground {
    private static EndlessScrollingBackground INSTANCE = new EndlessScrollingBackground();
    private SpriteBatch batch;
    private Texture img;
    private int xShift;
    private int dShift;

    private EndlessScrollingBackground() {


        xShift = 0;//starting point
        dShift = 5;//pace of shifting

        batch = new SpriteBatch();
        img = new Texture("background.png");
        img.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat); //image wrapping
    }

    void render() {
        xShift = xShift + dShift;

        batch.begin();
        batch.draw(img, 0, 0, xShift, 0, 5760, 1080);
        batch.end();
    }

    public static EndlessScrollingBackground getInstance() {
        return INSTANCE;
    }

    void dispose() {
        batch.dispose();
        img.dispose();
    }
}
