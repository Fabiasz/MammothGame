package com.gsxxx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Panel extends ApplicationAdapter {
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture image;
    private Sprite sprite;

    Panel() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font_01.fnt"));
        font.setColor(Color.DARK_GRAY);
        image = new Texture("hp.png");
        sprite = new Sprite(image);
    }
    public void dispose() {
        batch.dispose();
        font.dispose();
        image.dispose();
    }

    public void render() {
        batch.begin();
        font.draw(batch, "score", 50, 150);
        font.draw(batch, "hp", 1520, 150);
        sprite.setCenter(1700,120);
        sprite.draw(batch);
        batch.end();
    }
}
