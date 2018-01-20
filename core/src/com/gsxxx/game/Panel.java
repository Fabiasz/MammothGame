package com.gsxxx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.FloatArray;

public class Panel extends ApplicationAdapter {
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture hpImage;
    private Texture blank;
    private Sprite sprite;
    float colorRed;
    float colorGreen;
    float colorBlue;

    Panel() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font_01.fnt"));
        font.setColor(Color.DARK_GRAY);
        hpImage = new Texture("healthbar.png");
        sprite = new Sprite(hpImage);
        blank = new Texture("blank.png");
        //setting rgb of the healthbar
        colorRed = 0.6f;
        colorGreen = 0;
        colorBlue = 0;
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        hpImage.dispose();
    }

    public void render(float hp) {
        batch.begin();
        font.draw(batch, "score", 1600, 150);
        font.draw(batch, "hp", 230, 150);

        batch.setColor(colorRed, colorGreen, colorBlue, 1);
        batch.draw(blank, 40, 57, 150, 127 * hp);
        sprite.setCenter(120, 120);
        sprite.draw(batch);
        batch.end();
    }
}
