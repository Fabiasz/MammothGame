package com.gsxxx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public final class Panel extends ApplicationAdapter {
    private static Panel INSTANCE = new Panel();

    private SpriteBatch batch;
    private BitmapFont font;
    private Texture hpImage;
    private Texture blank;
    private Sprite sprite;
    private float colorRed;
    private float colorGreen;
    private float colorBlue;

    private Panel() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font_01.fnt"));
        font.setColor(Color.DARK_GRAY);
        hpImage = new Texture("healthbar.png");
        sprite = new Sprite(hpImage);
        blank = new Texture("blank.png");

        //setting rgb of the health bar
        colorRed = 0.6f;
        colorGreen = 0;
        colorBlue = 0;
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        hpImage.dispose();
    }

    public void render() {
        float hp = Mammoth.getInstance().health;
        batch.begin();
        font.draw(batch, "score", 1600, 150);
        font.draw(batch, "hp", 230, 150);
        if (hp >= 0) {
            batch.draw(blank, 40, 58, 150, 125 * hp);
        }
        if ((colorRed / hp) < 1.05) {
            batch.setColor(colorRed / hp, colorGreen, colorBlue, 1);
        } else {
            colorRed = 1f;
            colorGreen = 0.1f;
            colorBlue = 0.1f;
            batch.setColor(colorRed, colorGreen / hp, colorBlue / hp, 1);
        }
        sprite.setCenter(120, 120);
        sprite.draw(batch);
        batch.end();
    }

    public static Panel getInstance() {
        return INSTANCE;
    }
}
