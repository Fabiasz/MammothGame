package com.gsxxx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EndlessScrollingBackground {
    //single instantiation assurance
    private static boolean instantiated_ = false;
    private SpriteBatch batch;
    private Texture img;
    private int xShift;
    private int dShift;

    EndlessScrollingBackground() {
        assert(!instantiated_);
        instantiated_ = true;

        xShift = 0;//starting point
        dShift = 5;//pace of shifting

        batch = new SpriteBatch();
        img = new Texture("background.png");
        img.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat); //image wrapping
    }

    void render(){
        xShift = xShift + dShift;

        batch.begin();
        batch.draw(img,0,0, xShift, 0, 5760, 1080);
        batch.end();
    }

    void dispose(){
        instantiated_ = false;
        batch.dispose();
        img.dispose();
    }
}
