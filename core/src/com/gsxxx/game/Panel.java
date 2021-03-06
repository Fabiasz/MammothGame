package com.gsxxx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public final class Panel extends ApplicationAdapter {
    MammothGame game;
    private static Panel INSTANCE = new Panel();

    private SpriteBatch batch;
    private BitmapFont font;
    private Texture hpImage;
    private Texture blank;
    private Sprite sprite;
    private float colorRed;
    private float colorGreen;
    private float colorBlue;
    private float gameTimer;
    public int countDown = 10;

    //menu images properties variables
    private float buttonPositionX;
    private float buttonPlayPositionX;
    private float buttonPositionY;
    private float buttonPlayPositionY;
    private float buttonWidth;
    private float buttonHeight;
    Texture stopButton;
    Texture stopActiveButton;

    private Panel() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font_01.fnt"));
        font.setColor(Color.DARK_GRAY);
        hpImage = new Texture("healthbar.png");
        sprite = new Sprite(hpImage);
        blank = new Texture("blank.png");
        gameTimer = 0f;


        buttonPositionX = 1600;
        buttonPositionY = 800;
        buttonHeight = 200;
        buttonWidth = 200;
        stopButton = new Texture("quit.png");
        stopActiveButton = new Texture("quitactive.png");
    }

    public void dispose() {
        countDown = 10;
    }

    public void render() {
        float hp = Mammoth.getInstance().health;
        gameTimer += Gdx.graphics.getDeltaTime();
        batch.begin();
        batch.setColor(1, 1, 1, 1);

        if (Gdx.input.getX() > 1600 && Gdx.input.getX() < 1800 && Gdx.input.getY() > 50 && Gdx.input.getY() < 250) {
            batch.draw(stopActiveButton, buttonPositionX, buttonPositionY, buttonWidth, buttonHeight);
            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
            }
        } else {
            batch.draw(stopButton, buttonPositionX, buttonPositionY, buttonWidth, buttonHeight);
        }


        font.draw(batch, "" + countDown, 1700, 150);
        if (gameTimer >= 1) {
            countDown--;
            gameTimer = 0;
        }
        font.draw(batch, "hp", 230, 150);

        //setting rgb of the health bar
        if ((colorRed / hp) < 1.05) {
            colorRed = 0.6f;
            colorGreen = 0;
            colorBlue = 0;
            batch.setColor(colorRed / hp, colorGreen, colorBlue, 1);
        } else {
            colorRed = 1f;
            colorGreen = 0.1f;
            colorBlue = 0.1f;
            batch.setColor(colorRed, colorGreen / hp, colorBlue / hp, 1);
        }
        if (hp >= 0) {
            batch.draw(blank, 40, 58, 150, 125 * hp);
        }

        sprite.setCenter(120, 120);
        sprite.draw(batch);
        batch.end();
    }


    public static Panel getInstance() {
        return INSTANCE;
    }
}
