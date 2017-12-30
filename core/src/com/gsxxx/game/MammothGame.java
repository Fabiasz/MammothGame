package com.gsxxx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;

public class MammothGame extends ApplicationAdapter {

    EndlessScrollingBackground background;
    Mammoth mammoth;

    @Override
    public void create() {
        background = new EndlessScrollingBackground();
        mammoth = new Mammoth();
    }

    @Override
    public void render() {
        background.render();
        mammoth.render();
    }

    @Override
    public void dispose() {
        background.dispose();
        mammoth.dispose();
    }
}
