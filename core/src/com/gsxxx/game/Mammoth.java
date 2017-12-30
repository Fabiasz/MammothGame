package com.gsxxx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Mammoth {
    //single instantiation assurance
    private static boolean instantiated_ = false;
    private SpriteBatch batch;
    private Texture img;

    Mammoth() {
        assert (!instantiated_);
        instantiated_ = true;

        batch = new SpriteBatch();
        img = new Texture("mammoth.png");
    }

    void render() {
        batch.begin();
        batch.draw(img, 300, 300);
        batch.end();
    }

    void dispose() {
        instantiated_ = false;
        batch.dispose();
        img.dispose();
    }
}
